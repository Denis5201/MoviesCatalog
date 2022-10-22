package com.example.moviecatalog

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.screens.LoginScreen
import com.example.moviecatalog.screens.LoginViewModel
import com.example.moviecatalog.screens.MainScreen
import com.example.moviecatalog.screens.RegistrationScreen

@Composable
fun Navigate() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route ) {
        composable(Screen.LoginScreen.route) {
            val loginViewModel = viewModel<LoginViewModel>()
            LoginScreen(navController = navController, loginViewModel)
        }
        composable(Screen.RegistrationScreen.route) {
            RegistrationScreen(navController = navController)
        }
        composable(Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(Screen.MovieScreen.route) {
            MainScreen(navController = navController)
        }
    }
}