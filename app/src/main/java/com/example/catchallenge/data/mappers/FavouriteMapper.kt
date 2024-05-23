package com.example.catchallenge.data.mappers

import com.example.catchallenge.data.local.entities.FavouriteEntity
import com.example.catchallenge.domain.models.Breed

object FavouriteMapper {

    fun FavouriteEntity.toBreed() = Breed(
        id = id,
        name = name,
        origin = origin,
        lifeSpan = lifeSpan,
        temperament = temperament,
        description = description,
        imageUrl = imageUrl,
        isFavourite = true,
    )

    fun Breed.toFavouriteEntity () = FavouriteEntity(
        id = id,
        name = name,
        origin = origin,
        lifeSpan = lifeSpan,
        temperament = temperament,
        description = description,
        imageUrl = imageUrl,
    )
}