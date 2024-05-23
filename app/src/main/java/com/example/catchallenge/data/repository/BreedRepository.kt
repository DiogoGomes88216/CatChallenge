package com.example.catchallenge.data.repository

import androidx.paging.PagingSource
import com.example.catchallenge.data.local.dao.BreedDao
import com.example.catchallenge.data.local.dao.FavouriteDao
import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.mappers.BreedMapper.toBreed
import com.example.catchallenge.data.mappers.FavouriteMapper.toBreed
import com.example.catchallenge.data.mappers.FavouriteMapper.toFavouriteEntity
import com.example.catchallenge.domain.models.Breed
import javax.inject.Inject

class BreedRepository @Inject constructor(
    private val breedDao: BreedDao,
    private val favouriteDao: FavouriteDao,
) {

    fun getPagingSource(): PagingSource<Int, BreedEntity> {
        return breedDao.pagingSource()
    }

    suspend fun getBreedDetailsById(id: String): Breed {
        return breedDao.getBreedEntityById(id).toBreed(isFavourite(id))
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