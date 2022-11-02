package com.example.moviecatalog.domain

import kotlinx.serialization.Serializable

@Serializable
data class ReviewModifyModel(
    val reviewText: String,
    val rating: Int,
    val isAnonymous: Boolean
)
