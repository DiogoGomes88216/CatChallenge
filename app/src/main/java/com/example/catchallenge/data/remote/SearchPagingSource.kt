package com.example.catchallenge.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.repository.BreedRepository

class SearchPagingSource (
    private val repository: BreedRepository,
    private val query: String,
) : PagingSource<Int, BreedEntity>() {

    override fun getRefreshKey(state: PagingState<Int, BreedEntity>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1)?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(
        params: LoadParams<Int>,
    ): LoadResult<Int, BreedEntity> {

        return try {
            val result = repository.searchBreed(query = query)

            if (result.isSuccess) {
                val data = result.getOrNull() ?: emptyList()

                LoadResult.Page(
                    data = data,
                    prevKey = null,
                    nextKey = null
                )
            } else {
                LoadResult.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
            }

        } catch (ex: Exception){
            LoadResult.Error(ex)
        }
    }
}