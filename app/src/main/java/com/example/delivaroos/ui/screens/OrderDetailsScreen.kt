package com.example.delivaroos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delivaroos.viewmodels.OrderViewModel

@Composable
fun OrderDetailsScreen(
    orderId: String,
    navController: NavController,
    viewModel: OrderViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Order Details Screen",
            style = MaterialTheme.typography.h4
        )
        Text(
            text = "Order ID: $orderId",
            style = MaterialTheme.typography.subtitle1
        )
        // Add your order details content here
    }
} 