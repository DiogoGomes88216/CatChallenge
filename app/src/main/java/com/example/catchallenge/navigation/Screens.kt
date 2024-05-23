package com.example.catchallenge.navigation

import kotlinx.serialization.Serializable

internal sealed class Screens {

    @Serializable
    object BreedListScreen

    @Serializable
    object FavouritesScreen

    @Serializable
    data class BreedDetailsScreen(val id: String)
}