package com.example.catchallenge.data.mappers

import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.remote.models.BreedDto
import com.example.catchallenge.domain.models.Breed

object BreedMapper {

    fun BreedDto.toBreed(isFavourite: Boolean) = Breed(
        id = id,
        name = name,
        origin = origin,
        lifeSpan = lifeSpan,
        temperament = temperament,
        description = description,
        imageUrl = image?.url,
        isFavourite = isFavourite,
    )

    fun BreedDto.toBreedEntity(isFavourite: Boolean) = BreedEntity(
        id = id,
        name = name,
        origin = origin,
        lifeSpan = lifeSpan,
        temperament = temperament,
        description = description,
        imageUrl = image?.url,
        isFavourite = isFavourite
    )

    fun BreedEntity.toBreed() = Breed(
        id = id,
        name = name,
        origin = origin,
        lifeSpan = lifeSpan,
        temperament = temperament,
        description = description,
        imageUrl = imageUrl,
        isFavourite = isFavourite,
    )

    fun Breed.toBreedEntity() = BreedEntity(
        id = id,
        name = name,
        origin = origin,
        lifeSpan = lifeSpan,
        temperament = temperament,
        description = description,
        imageUrl = imageUrl,
        isFavourite = isFavourite,
    )

}