package com.example.catchallenge.presentation.breedDetails

import com.example.catchallenge.domain.models.Breed

data class BreedDetailsState(
    val breed: Breed = Breed(
        id = "",
        name = "",
        origin = "",
        lifeSpan = "",
        temperament = "",
        description = "",
        imageUrl = "",
        isFavourite = false,
    )
)
