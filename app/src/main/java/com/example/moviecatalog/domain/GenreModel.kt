package com.example.moviecatalog.domain

import kotlinx.serialization.Serializable

@Serializable
data class GenreModel(
    val id: String,
    val name: String
)
