package com.example.catchallenge.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.catchallenge.data.local.db.BreedDatabase
import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.local.entities.RemoteKeys
import com.example.catchallenge.data.remote.api.BreedApi
import com.example.catchallenge.domain.mappers.BreedMapper.toBreedEntity
import com.example.catchallenge.utils.Constants.PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class BreedRemoteMediator @Inject constructor(
    private val api: BreedApi,
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
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            val results = api.getBreeds(limit = limit, page = page)

            val domainData = results.map {dto ->
                dto.toBreedEntity(imageUrl = getImageUrl(dto.referenceImageId))
            }

            val endOfPaginationReached = domainData.isEmpty()

            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    db.breedDao.clearBreedEntity()
                }
                val prevKey = if (page == initialPage) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = domainData.map {
                    RemoteKeys(name = it.name, prevKey = prevKey, nextKey = nextKey)
                }
                db.remoteKeysDao().insertAll(keys)
                db.breedDao.insertBreedEntities(domainData)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private fun getImageUrl(imageId: String): String {
        return "https://cdn2.thecatapi.com/images/$imageId.jpg"
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, BreedEntity>
    ): RemoteKeys? {

        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { breed ->
                db.remoteKeysDao().getRemoteKeysByBreed(name = breed.name)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, BreedEntity>
    ): RemoteKeys? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { name ->
                db.remoteKeysDao().getRemoteKeysByBreed(name = name)
            }
        }
    }
}