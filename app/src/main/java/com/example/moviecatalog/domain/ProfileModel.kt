package com.example.moviecatalog.domain

import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel(
    val id: String,
    val nickName: String,
    val email: String,
    val avatarLink: String?,
    val name: String,
    var birthDate: String,
    val gender: Int
)
