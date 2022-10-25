package com.example.moviecatalog.domain

data class Review(
    val id: String,
    val rating: Int,
    val reviewText: String,
    val isAnonymous: Boolean,
    val createDateTime: String,
    val author: UserShort,
)
