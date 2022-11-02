package com.example.moviecatalog.domain

import kotlinx.serialization.Serializable

@Serializable
data class ReviewModel(
    val id: String,
    val rating: Int,
    val reviewText: String?,
    val isAnonymous: Boolean,
    val createDateTime: String,
    val author: UserShortModel
)
