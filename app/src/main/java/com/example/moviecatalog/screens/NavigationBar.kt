package com.example.moviecatalog.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.R

@Composable
fun NavigationBar(navController: NavController) {
    val navBarController = rememberNavController()
    Scaffold(
        bottomBar = {
            val items = listOf(BottomNavItem.Main, BottomNavItem.Profile)
            BottomNavigation (
                backgroundColor = colorResource(R.color.bottomBar)
                    ) {
                val navBackStackEntry by navBarController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { item ->
                    BottomNavigationItem(
                        icon = { Icon(ImageVector.vectorResource(item.icon), null) },
                        label = { Text(text = item.name, style = MaterialTheme.typography.body1) },
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = MaterialTheme.colors.secondaryVariant,
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navBarController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navBarController, startDestination = BottomNavItem.Main.route, Modifier.padding(innerPadding)) {
            composable(BottomNavItem.Main.route) { MainScreen(navController) }
            composable(BottomNavItem.Profile.route) { ProfileScreen(navController) }
        }
    }
}