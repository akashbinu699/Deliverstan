package com.example.delivaroos.ui.screens

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.delivaroos.navigation.NavScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val bottomNavItems = listOf(
        NavScreen.Home,
        NavScreen.Cart,
        NavScreen.Orders,
        NavScreen.Profile
    )

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = when (screen) {
                                    NavScreen.Home -> Icons.Default.Home
                                    NavScreen.Cart -> Icons.Default.ShoppingCart
                                    NavScreen.Orders -> Icons.Default.Receipt
                                    NavScreen.Profile -> Icons.Default.Person
                                    else -> Icons.Default.Home
                                },
                                contentDescription = screen.route
                            )
                        },
                        label = { Text(screen.route) },
                        selected = currentDestination?.route == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { _ ->
        MainAppContent()
    }
} 