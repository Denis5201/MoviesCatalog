package com.example.moviecatalog.screens

import com.example.moviecatalog.R

sealed class BottomNavItem(val route: String, val icon: Int, val name: String) {
    object Main : BottomNavItem("Main", R.drawable.main_icon, "Главное")
    object Profile : BottomNavItem("Profile", R.drawable.profile_icon, "Профиль")
}
