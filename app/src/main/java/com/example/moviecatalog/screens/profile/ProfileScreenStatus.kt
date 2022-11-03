package com.example.moviecatalog.screens.profile

data class ProfileScreenStatus(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMassage: String? = null,
    val isSaveSuccess: Boolean = false
)
