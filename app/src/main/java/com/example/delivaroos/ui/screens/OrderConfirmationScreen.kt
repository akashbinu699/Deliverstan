package com.example.delivaroos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.delivaroos.navigation.NavScreen

@Composable
fun OrderConfirmationScreen(
    navController: NavController,
    orderId: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Success",
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colors.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Order Placed Successfully!",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Your order #${orderId.takeLast(6)} has been placed successfully.",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "You can track your order in the Orders section.",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate(NavScreen.Orders.route) {
                    popUpTo(NavScreen.Cart.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Orders")
        }

        TextButton(
            onClick = {
                navController.navigate(NavScreen.Home.route) {
                    popUpTo(NavScreen.Cart.route) { inclusive = true }
                }
            }
        ) {
            Text("Continue Shopping")
        }
    }
} 