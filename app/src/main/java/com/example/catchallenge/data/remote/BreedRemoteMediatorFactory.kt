package com.example.catchallenge.data.remote

import com.example.catchallenge.data.local.db.BreedDatabase
import com.example.catchallenge.data.repository.BreedRepository
import javax.inject.Inject

class BreedRemoteMediatorFactory @Inject constructor(
    private val repository: BreedRepository,
    private val db: BreedDatabase
)  {
    fun createMediator(
        searchQuery: String
    ) = BreedRemoteMediator(
        repository = repository,
        db = db,
        searchQuery = searchQuery,
    )
}