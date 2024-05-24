package com.example.catchallenge.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchallenge.data.repository.BreedRepository
import com.example.catchallenge.domain.models.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repository: BreedRepository,
): ViewModel() {

    private val _favouritesState = MutableStateFlow(FavouritesState())
    val favouritesState: StateFlow<FavouritesState> by lazy {
        observeFavourites()
        _favouritesState
    }

    private fun observeFavourites() {
        viewModelScope.launch {
            repository.getFavouritesFlow()
                .collect { result ->
                    result.onSuccess { favourites ->
                        _favouritesState.update {
                            it.copy(favourites = favourites, isLoading = false, hasError = false)
                        }
                    }.onFailure { ex ->
                        _favouritesState.update {
                            it.copy(isLoading = false, hasError = true, error = ex.toString())
                        }
                    }
                }
        }
    }

    fun toggleFavourite(breed: Breed) {
        viewModelScope.launch {
            if(breed.isFavourite) {
                repository.removeFavourite(breed.id)
            } else {
                repository.addFavourite(breed)
            }
        }
    }
}