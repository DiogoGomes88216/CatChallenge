package com.example.catchallenge.presentation.favourites

import com.example.catchallenge.domain.models.Breed

data class FavouritesState(
    val favourites: List<Breed> = emptyList(),
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
)
