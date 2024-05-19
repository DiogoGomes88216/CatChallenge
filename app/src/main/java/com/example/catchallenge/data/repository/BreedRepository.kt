package com.example.catchallenge.data.repository

import androidx.paging.PagingSource
import com.example.catchallenge.data.local.dao.BreedDao
import com.example.catchallenge.data.local.entities.BreedEntity
import javax.inject.Inject

class BreedRepository @Inject constructor(
    private val dao: BreedDao,
) {

    fun getPagingSource(): PagingSource<Int, BreedEntity> {
        return dao.pagingSource()
    }
}