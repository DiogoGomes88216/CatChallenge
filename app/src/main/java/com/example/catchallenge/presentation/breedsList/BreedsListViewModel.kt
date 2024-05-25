package com.example.catchallenge.presentation.breedsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.catchallenge.data.mappers.BreedMapper.toBreed
import com.example.catchallenge.data.remote.BreedRemoteMediatorFactory
import com.example.catchallenge.data.repository.BreedRepository
import com.example.catchallenge.domain.models.Breed
import com.example.catchallenge.utils.Constants.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class BreedsListViewModel @Inject constructor(
    private val repository: BreedRepository,
    mediatorFactory: BreedRemoteMediatorFactory,
): ViewModel() {

    private val _breedsListState = MutableStateFlow(BreedsListState())
    val breedsListState: StateFlow<BreedsListState> by lazy {
        _breedsListState
    }

    private val _search = MutableStateFlow("")
    val search: StateFlow<String> by lazy {
        _search
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class, FlowPreview::class)
    val breedPagingFlow = search.debounce(300.milliseconds).flatMapLatest {
        Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true,
            ),
            remoteMediator = mediatorFactory.createMediator(_search.value)
        ) {
            repository.getPagingSource()
        }.flow.map { pagingData ->
            pagingData.map {
                it.toBreed()
            }
        }.cachedIn(viewModelScope)
    }.cachedIn(viewModelScope)

    fun toggleFavourite(breed: Breed) {
        viewModelScope.launch {
            repository.toggleFavourite(breed)
        }
    }

    fun clearSearch() {
        _search.value = ""
    }

    fun setSearch(query: String) {
        _search.value = query
    }

    fun toggleIsSearchShowing() {
        _breedsListState.update {
            it.copy(isSearchShowing = !_breedsListState.value.isSearchShowing)
        }
    }

    fun retry(){
        _breedsListState.update {
            it.copy(retry = !_breedsListState.value.retry)
        }
    }
}