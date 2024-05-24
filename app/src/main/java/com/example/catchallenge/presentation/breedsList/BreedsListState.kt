package com.example.catchallenge.presentation.breedsList

data class BreedsListState(
    val search: String = "",
    val isSearchShowing: Boolean = false,
    val favouriteToggle: Boolean = false
)
