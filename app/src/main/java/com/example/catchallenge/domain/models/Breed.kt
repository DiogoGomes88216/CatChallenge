package com.example.catchallenge.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Breed(
    val name: String,
    val origin: String,
    val lifeSpan: String,
    val temperament: String,
    val description: String,
    val imageUrl: String?,
    val isFavourite: Boolean,
)
