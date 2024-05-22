package com.example.catchallenge.navigation

import com.example.catchallenge.domain.models.Breed
import kotlinx.serialization.Serializable

internal sealed class Screens {

    @Serializable
    object BreedlistScreen

    @Serializable
    data class BreedDetailsScreen(val id: String)
}