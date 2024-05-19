package com.example.catchallenge.domain.mappers

import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.remote.models.BreedDto
import com.example.catchallenge.domain.models.Breed

object BreedMapper {

    fun BreedDto.toBreed(imageUrl: String) = Breed(
        name = name,
        origin = origin,
        lifeSpan = lifeSpan,
        temperament = temperament,
        description = description,
        imageUrl = imageUrl,
    )

    fun BreedDto.toBreedEntity(imageUrl: String) = BreedEntity(
        name = name,
        origin = origin,
        lifeSpan = lifeSpan,
        temperament = temperament,
        description = description,
        imageUrl = imageUrl,
    )

    fun BreedEntity.toBreed() = Breed(
        name = name,
        origin = origin,
        lifeSpan = lifeSpan,
        temperament = temperament,
        description = description,
        imageUrl = imageUrl,
    )
}