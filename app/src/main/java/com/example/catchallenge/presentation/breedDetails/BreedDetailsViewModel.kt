package com.example.catchallenge.presentation.breedDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.example.catchallenge.data.repository.BreedRepository
import com.example.catchallenge.domain.models.Breed
import com.example.catchallenge.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailsViewModel @Inject constructor(
    private val repository: BreedRepository,
    stateHandle: SavedStateHandle,
): ViewModel() {

    private val _breedDetailsState = MutableStateFlow(BreedDetailsState())
    val breedDetailsState: StateFlow<BreedDetailsState> by lazy {
        getBreedDetailsById(stateHandle.toRoute<Screens.BreedDetailsScreen>().id)
        _breedDetailsState
    }

    private fun getBreedDetailsById(id: String) {
        viewModelScope.launch {
            val breed = repository.getBreedDetailsById(id = id)
            _breedDetailsState.update {
                it.copy(breed = breed)
            }
        }
    }

    fun toggleFavourite() {
        /*viewModelScope.launch {
            val breed = Breed()
        }
        _breedDetailsState.update {
            it.copy(breed = breed)
        }*/
    }
}