package com.example.catchallenge.data.remote.api

import com.example.catchallenge.data.remote.models.BreedDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BreedApi {

    @GET("breeds")
    suspend fun getBreeds(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
    ): List<BreedDto>

}