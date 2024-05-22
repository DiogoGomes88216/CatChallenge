package com.example.catchallenge.domain.models

data class Breed(
    val id: String,
    val name: String,
    val origin: String,
    val lifeSpan: String,
    val temperament: String,
    val description: String,
    val imageUrl: String?,
    val isFavourite: Boolean,
)
