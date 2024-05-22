package com.example.catchallenge.data.repository

import androidx.paging.PagingSource
import com.example.catchallenge.data.local.dao.BreedDao
import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.mappers.BreedMapper.toBreed
import com.example.catchallenge.domain.models.Breed
import javax.inject.Inject

class BreedRepository @Inject constructor(
    private val dao: BreedDao,
) {

    fun getPagingSource(): PagingSource<Int, BreedEntity> {
        return dao.pagingSource()
    }

    fun isFavourite(id: String): Boolean {
        return true
    }

    suspend fun getBreedDetailsById(id: String): Breed {
        return dao.getBreedEntityById(id).toBreed(isFavourite(id))
    }
}