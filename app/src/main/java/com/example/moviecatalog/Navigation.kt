package com.example.moviecatalog

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.screens.LoginScreen
import com.example.moviecatalog.screens.MainScreen
import com.example.moviecatalog.screens.NavigationBar
import com.example.moviecatalog.screens.RegistrationScreen

@Composable
fun Navigate() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.RegistrationScreen.route) {
            RegistrationScreen(navController = navController)
        }
        composable(Screen.MainScreen.route) {
            NavigationBar(navController = navController)
        }
        composable(Screen.MovieScreen.route) {
            MainScreen(navController = navController)
        }
    }
}