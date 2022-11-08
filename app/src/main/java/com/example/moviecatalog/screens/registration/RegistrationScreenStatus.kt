package com.example.moviecatalog.screens.registration

data class RegistrationScreenStatus(
    val showMessage: Boolean = false,
    val textMessage: String? = null,
    val mayGoToMain: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
