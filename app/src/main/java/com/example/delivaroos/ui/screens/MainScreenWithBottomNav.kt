package com.example.delivaroos.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.delivaroos.navigation.NavScreen
import com.example.delivaroos.viewmodels.CartViewModel
import com.example.delivaroos.viewmodels.FoodViewModel
import com.example.delivaroos.viewmodels.OrderViewModel

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(NavScreen.Home.route, "Home", Icons.Default.Home)
    object Cart : BottomNavItem(NavScreen.Cart.route, "Cart", Icons.Default.ShoppingCart)
    object Orders : BottomNavItem(NavScreen.Orders.route, "Orders", Icons.Default.List)
    object Profile : BottomNavItem(NavScreen.Profile.route, "Profile", Icons.Default.Person)

    companion object {
        val items = listOf(Home, Cart, Orders, Profile)
    }
}

@Composable
fun MainScreenWithBottomNav(
    parentNavController: NavHostController,
    cartViewModel: CartViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                BottomNavItem.items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
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
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    foodViewModel = FoodViewModel(),
                    cartViewModel = cartViewModel
                )
            }
            composable(BottomNavItem.Cart.route) {
                CartScreen(
                    navController = parentNavController,
                    cartViewModel = cartViewModel
                )
            }
            composable(BottomNavItem.Orders.route) {
                OrdersScreen(
                    navController = parentNavController,
                    orderViewModel = OrderViewModel()
                )
            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(navController = parentNavController)
            }
        }
    }
} 