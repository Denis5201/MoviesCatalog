package com.example.moviecatalog

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.screens.NavigationBar
import com.example.moviecatalog.screens.login.LoginScreen
import com.example.moviecatalog.screens.login.LoginViewModel
import com.example.moviecatalog.screens.main.MainScreen
import com.example.moviecatalog.screens.registration.RegistrationScreen
import com.example.moviecatalog.screens.registration.RegistrationViewModel

@Composable
fun Navigate() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route ) {
        composable(Screen.LoginScreen.route) {
            val loginViewModel = viewModel<LoginViewModel>()
            LoginScreen(navController = navController, loginViewModel)
        }
        composable(Screen.RegistrationScreen.route) {
            val registrationViewModel = viewModel<RegistrationViewModel>()
            RegistrationScreen(navController = navController, registrationViewModel)
        }
        composable(Screen.MainScreen.route) {
            NavigationBar(navController = navController)
        }
        composable(Screen.MovieScreen.route) {
            MainScreen(navController = navController)
        }
    }
}