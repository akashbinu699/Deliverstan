package com.example.delivaroos.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavScreen(val route: String) {
    object Login : NavScreen("login")
    object Register : NavScreen("register")
    object Home : NavScreen("home")
    object Cart : NavScreen("cart")
    object Orders : NavScreen("orders")
    object Profile : NavScreen("profile")
    object OrderDetail : NavScreen("order_detail") {
        val arguments = listOf(
            navArgument("orderId") { type = NavType.StringType }
        )

        fun createRoute(orderId: String) = "$route/$orderId"
    }
    object OrderConfirmation : NavScreen("order_confirmation") {
        val arguments = listOf(
            navArgument("orderId") { type = NavType.StringType }
        )

        fun createRoute(orderId: String) = "$route/$orderId"
    }
} 