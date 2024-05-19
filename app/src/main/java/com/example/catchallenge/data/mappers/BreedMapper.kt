package com.example.catchallenge.data.mappers

import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.remote.models.BreedDto
import com.example.catchallenge.domain.models.Breed

object BreedMapper {

    fun BreedDto.toBreed() = Breed(
        name = name,
        origin = origin,
        lifeSpan = lifeSpan,
        temperament = temperament,
        description = description,
        imageUrl = image?.url,

    )

    fun BreedDto.toBreedEntity() = BreedEntity(
        id = id,
        name = name,
        origin = origin,
        lifeSpan = lifeSpan,
        temperament = temperament,
        description = description,
        imageUrl = image?.url,
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