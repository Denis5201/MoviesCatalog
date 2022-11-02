package com.example.moviecatalog.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserShortModel(
    val userId: String,
    val nickName: String,
    val avatar: String?
)
