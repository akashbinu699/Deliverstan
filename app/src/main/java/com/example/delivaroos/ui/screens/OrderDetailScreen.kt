package com.example.delivaroos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.delivaroos.models.Order
import com.example.delivaroos.models.OrderStatus
import com.example.delivaroos.ui.components.OrderItemCard
import com.example.delivaroos.ui.components.ScreenLayout
import com.example.delivaroos.viewmodels.OrderViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.text.TextStyle

@Composable
fun OrderDetailScreen(
    orderId: String,
    navController: NavController,
    orderViewModel: OrderViewModel
) {
    ScreenLayout(
        title = "Order Details",
        navController = navController,
        showBackButton = true
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Existing order detail content
        }
    }
}

@Composable
private fun OrderDetail(
    order: Order,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Order Status
        item {
            OrderStatusSection(order.status)
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Order Info
        item {
            OrderInfoSection(order)
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Order Items
        item {
            Text(
                text = "Order Items",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(order.items) { item ->
            OrderItemCard(cartItem = item)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Price Summary
        item {
            PriceSummarySection(order)
        }
    }
}

@Composable
private fun OrderStatusSection(status: OrderStatus) {
    Column {
        Text(
            text = "Order Status",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = when (status) {
                OrderStatus.PENDING -> MaterialTheme.colors.primary.copy(alpha = 0.1f)
                OrderStatus.PREPARING -> MaterialTheme.colors.secondary.copy(alpha = 0.1f)
                OrderStatus.DELIVERING -> MaterialTheme.colors.primaryVariant.copy(alpha = 0.1f)
                OrderStatus.DELIVERED -> MaterialTheme.colors.primary.copy(alpha = 0.1f)
                OrderStatus.CANCELLED -> MaterialTheme.colors.error.copy(alpha = 0.1f)
            }
        ) {
            Text(
                text = status.name,
                modifier = Modifier.padding(16.dp),
                color = when (status) {
                    OrderStatus.PENDING -> MaterialTheme.colors.primary
                    OrderStatus.PREPARING -> MaterialTheme.colors.secondary
                    OrderStatus.DELIVERING -> MaterialTheme.colors.primaryVariant
                    OrderStatus.DELIVERED -> MaterialTheme.colors.primary
                    OrderStatus.CANCELLED -> MaterialTheme.colors.error
                }
            )
        }
    }
}

@Composable
private fun OrderInfoSection(order: Order) {
    Column {
        Text(
            text = "Order Information",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow("Order Date", formatDate(order.orderDate))
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow("Delivery Address", order.deliveryAddress)
                if (order.status == OrderStatus.DELIVERED) {
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoRow("Delivered At", formatDate(order.deliveredAt!!))
                }
            }
        }
    }
}

@Composable
private fun PriceSummarySection(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoRow("Subtotal", "£${String.format("%.2f", order.subtotal)}")
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow("Delivery Fee", "£${String.format("%.2f", order.deliveryFee)}")
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(
                "Total",
                "£${String.format("%.2f", order.total)}",
                MaterialTheme.typography.h6
            )
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    textStyle: TextStyle = MaterialTheme.typography.body1
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = textStyle)
        Text(text = value, style = textStyle, fontWeight = FontWeight.Bold)
    }
}

private fun formatDate(date: Date): String {
    return SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(date)
} 