package com.example.moviecatalog.screens.login

data class LoginScreenStatus(
    val showMessage: Boolean = false,
    val textMessage: String? = null,
    val mayGoToMain: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
