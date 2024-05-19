package com.example.catchallenge.presentation.CatBreedsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.catchallenge.data.remote.BreedRemoteMediator
import com.example.catchallenge.data.repository.BreedRepository
import com.example.catchallenge.data.mappers.BreedMapper.toBreed
import com.example.catchallenge.utils.Constants.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CatBreedsListViewModel @Inject constructor(
    private val repository: BreedRepository,
    private val remoteMediator: BreedRemoteMediator
): ViewModel() {


    @OptIn(ExperimentalPagingApi::class)
    val breedPager = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true,
            prefetchDistance = 3 * PAGE_SIZE,
            initialLoadSize = 2 * PAGE_SIZE,
        ),
        remoteMediator = remoteMediator,
        pagingSourceFactory = {
            repository.getPagingSource()
        }
    ).flow
        .map {pagingData ->
            pagingData.map {
                it.toBreed()
            }
        }
        .cachedIn(viewModelScope)
}