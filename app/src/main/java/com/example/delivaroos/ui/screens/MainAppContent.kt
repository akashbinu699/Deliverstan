package com.example.delivaroos.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.delivaroos.viewmodels.CartViewModel

@Composable
fun MainAppContent() {
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()
    MainScreenWithBottomNav(
        parentNavController = navController,
        cartViewModel = cartViewModel
    )
} 