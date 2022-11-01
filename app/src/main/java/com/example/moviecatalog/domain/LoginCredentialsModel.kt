package com.example.moviecatalog.domain

import kotlinx.serialization.Serializable

@Serializable
data class LoginCredentialsModel(
    val username: String,
    val password: String
)
