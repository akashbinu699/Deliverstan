package com.example.delivaroos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.delivaroos.ui.screens.*
import com.example.delivaroos.viewmodels.*
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val cartViewModel = viewModel<CartViewModel>()

    NavHost(
        navController = navController,
        startDestination = NavScreen.Login.route
    ) {
        composable(NavScreen.Login.route) {
            LoginScreen(navController = navController)
        }
        
        composable(NavScreen.Register.route) {
            RegisterScreen(navController = navController)
        }
        
        composable(NavScreen.Home.route) {
            MainScreenWithBottomNav(
                parentNavController = navController,
                cartViewModel = cartViewModel
            )
        }
        
        composable(
            route = NavScreen.OrderDetail.route + "/{orderId}",
            arguments = NavScreen.OrderDetail.arguments
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: return@composable
            OrderDetailScreen(
                orderId = orderId,
                navController = navController,
                orderViewModel = OrderViewModel()
            )
        }

        composable(
            route = NavScreen.OrderConfirmation.route + "/{orderId}",
            arguments = NavScreen.OrderConfirmation.arguments
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: return@composable
            OrderConfirmationScreen(
                navController = navController,
                orderId = orderId
            )
        }
    }
} 