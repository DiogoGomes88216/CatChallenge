package com.example.catchallenge.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BreedEntity(
    @PrimaryKey val name: String,
    val origin: String,
    val lifeSpan: String,
    val temperament: String,
    val description: String,
    val imageUrl: String,
)
