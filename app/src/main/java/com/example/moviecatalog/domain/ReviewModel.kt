package com.example.moviecatalog.domain

import kotlinx.serialization.Serializable

@Serializable
data class ReviewModel(
    val id: String,
    var rating: Int,
    var reviewText: String?,
    var isAnonymous: Boolean,
    val createDateTime: String,
    val author: UserShortModel?
)
