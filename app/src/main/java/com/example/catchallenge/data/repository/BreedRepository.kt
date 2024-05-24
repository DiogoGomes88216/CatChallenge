package com.example.catchallenge.data.repository

import androidx.paging.PagingSource
import com.example.catchallenge.data.local.dao.BreedDao
import com.example.catchallenge.data.local.dao.FavouriteDao
import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.mappers.BreedMapper.toBreed
import com.example.catchallenge.data.mappers.BreedMapper.toBreedEntity
import com.example.catchallenge.data.mappers.FavouriteMapper.toBreed
import com.example.catchallenge.data.mappers.FavouriteMapper.toFavouriteEntity
import com.example.catchallenge.data.remote.api.BreedApi
import com.example.catchallenge.domain.models.Breed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BreedRepository @Inject constructor(
    private val api: BreedApi,
    private val breedDao: BreedDao,
    private val favouriteDao: FavouriteDao,
) {

    fun getPagingSource(): PagingSource<Int, BreedEntity> {
        return breedDao.pagingSource()
    }

    suspend fun searchBreed(query: String): List<BreedEntity> {
        return try {
            val results = api.searchBreeds(query = query)
            val breedEntities = results.map {
                it.toBreedEntity(isFavourite(it.id))
            }
            breedEntities
        } catch (ex: Exception){
            emptyList()
        }
    }

    suspend fun getBreeds(limit: Int, page: Int): List<BreedEntity> {
        val results = api.getBreeds(limit = limit, page = page)

        return results.map {dto ->
            dto.toBreedEntity(isFavourite(dto.id))
        }
    }

    suspend fun getBreedDetailsById(id: String): Breed {
        val dbResult = breedDao.getBreedEntityById(id)
        return dbResult.toBreed()
    }

    suspend fun isFavourite(id: String): Boolean {
        return favouriteDao.hasFavourite(id)
    }

    suspend fun toggleFavourite(breed: Breed) {
        if (breed.isFavourite) {
            favouriteDao.deleteFavouriteEntity(breed.toFavouriteEntity())
            breedDao.updateBreedEntity(breed.copy(isFavourite = false).toBreedEntity())
        } else {
            favouriteDao.insertFavouriteEntity(breed.toFavouriteEntity())
            breedDao.updateBreedEntity(breed.copy(isFavourite = true).toBreedEntity())
        }
    }

    fun getFavouritesFlow(): Flow<List<Breed>> {
        return favouriteDao.getFavouritesFlow().map { list ->
                list.map { it.toBreed() }
            }
    }
}