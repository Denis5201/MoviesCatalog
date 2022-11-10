package com.example.moviecatalog

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login")
    object RegistrationScreen : Screen("registration")
    object MainScreen : Screen("main")
    object MovieScreen : Screen("movie")
}
