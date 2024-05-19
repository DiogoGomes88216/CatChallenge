package com.example.catchallenge.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class WeightDto(
    @SerialName("imperial")
    val imperial: String,
    @SerialName("metric")
    val metric: String
)