package com.example.moviecatalog.screens.profile

data class ProfileScreenStatus(
    val isLoading: Boolean = false,
    val showMessage: Boolean = false,
    val textMessage: String? = null,
    val isError: Boolean = false,
    val errorMassage: String? = null,
    val logout: Boolean = false
)
