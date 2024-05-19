package com.example.catchallenge.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class BreedDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("origin")
    val origin: String,
    @SerialName("life_span")
    val lifeSpan: String,
    @SerialName("description")
    val description: String,
    @SerialName("temperament")
    val temperament: String,
    @SerialName("image")
    val image: ImageDto?,
)