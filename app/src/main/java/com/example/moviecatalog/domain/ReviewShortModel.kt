package com.example.moviecatalog.domain

import kotlinx.serialization.Serializable

@Serializable
data class ReviewShortModel(
    val id: String,
    val rating: Int
)
