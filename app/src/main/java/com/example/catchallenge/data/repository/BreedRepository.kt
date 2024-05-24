package com.example.catchallenge.data.repository

import androidx.paging.PagingSource
import com.example.catchallenge.data.local.dao.BreedDao
import com.example.catchallenge.data.local.dao.FavouriteDao
import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.mappers.BreedMapper.toBreed
import com.example.catchallenge.data.mappers.BreedMapper.toBreedEntity
import com.example.catchallenge.data.mappers.FavouriteMapper.toBreed
import com.example.catchallenge.data.mappers.FavouriteMapper.toFavouriteEntity
import com.example.catchallenge.data.remote.SearchPagingSource
import com.example.catchallenge.data.remote.api.BreedApi
import com.example.catchallenge.domain.models.Breed
import javax.inject.Inject

class BreedRepository @Inject constructor(
    private val breedDao: BreedDao,
    private val favouriteDao: FavouriteDao,
    private val api: BreedApi
) {

    fun getPagingSource(query: String): PagingSource<Int, BreedEntity> {
        return if(query.isEmpty())
            breedDao.pagingSource()
        else
            SearchPagingSource(this, query)
    }

    suspend fun insertBreed(breed: Breed) {
        breedDao.insertBreedEntity(breed.toBreedEntity())
    }

    suspend fun searchBreed(query: String): Result<List<BreedEntity>> {
        return try {
            val results = api.searchBreeds(query = query)
            val breedEntities = results.map {
                it.toBreedEntity()
            }
            Result.success(breedEntities)
        } catch (ex: Exception){
            Result.failure(ex)
        }
    }

    suspend fun getBreedDetailsById(id: String): Breed {
        val dbResult = breedDao.getBreedEntityById(id)
        return dbResult.toBreed(isFavourite(id))
    }

    suspend fun isFavourite(id: String): Boolean {
        return (favouriteDao.hasFavourite(id))
    }

    suspend fun removeFavourite(id: String) {
        return favouriteDao.deleteFavouriteEntity(id)
    }

    suspend fun addFavourite(breed: Breed) {
        return favouriteDao.insertFavouriteEntity(breed.toFavouriteEntity())
    }

    suspend fun getFavourites(): Result<List<Breed>> {

        val dbResult = favouriteDao.getFavourites()
        dbResult?.let {
            return Result.success(
                it.map { favourite ->
                    favourite.toBreed()
                } )
        }
        return Result.failure(Exception("Not Found"))
    }
}