package com.example.catchallenge.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.catchallenge.data.local.db.BreedDatabase
import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.local.entities.RemoteKeys
import com.example.catchallenge.data.repository.BreedRepository
import com.example.catchallenge.utils.Constants.PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BreedRemoteMediator(
    private val repository: BreedRepository,
    private val searchQuery: String,
    private val db: BreedDatabase
) : RemoteMediator<Int, BreedEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BreedEntity>
    ): MediatorResult {

        val limit = PAGE_SIZE
        val initialPage = 0

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: initialPage
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val (breedEntities, endOfPaginationReached) = if(searchQuery.isNotEmpty()) {
                repository.searchBreed(searchQuery) to true
            } else {
                val domainData = repository.getBreeds(limit = limit, page = page)
                domainData to domainData.isEmpty()
            }

            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    db.breedDao.clearBreedEntity()
                }
                val prevKey = if (page == initialPage) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = breedEntities.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                db.remoteKeysDao().insertAll(keys)
                db.breedDao.insertBreedEntities(breedEntities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, BreedEntity>
    ): RemoteKeys? {

        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { breed ->
                db.remoteKeysDao().getRemoteKeysBy(id = breed.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, BreedEntity>
    ): RemoteKeys? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.remoteKeysDao().getRemoteKeysBy(id = id)
            }
        }
    }
}