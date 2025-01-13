package com.example.delivaroos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.delivaroos.models.CartItem
import com.example.delivaroos.models.Order
import com.example.delivaroos.models.OrderStatus
import com.example.delivaroos.ui.components.ScreenLayout
import com.example.delivaroos.viewmodels.OrderViewModel
import java.text.SimpleDateFormat
import java.util.*

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
        val order by orderViewModel.getOrder(orderId).observeAsState()
        val isLoading by orderViewModel.isLoading.observeAsState(false)
        val error by orderViewModel.error.observeAsState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                error != null -> {
                    Text(
                        text = error ?: "Error loading order",
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colors.error
                    )
                }
                order != null -> {
                    OrderDetails(order = order!!)
                }
            }
        }
    }
}

@Composable
private fun OrderDetails(order: Order) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            OrderHeader(order)
            Divider(modifier = Modifier.padding(vertical = 16.dp))
        }

        items(order.items) { item ->
            OrderItemRow(item)
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            OrderSummary(order)
        }
    }
}

@Composable
private fun OrderHeader(order: Order) {
    Column {
        Text(
            text = "Order #${order.id.takeLast(6)}",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = formatDate(order.orderDate),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OrderStatusChip(status = order.status)
    }
}

@Composable
private fun OrderItemRow(item: CartItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(
                text = "${item.quantity}x",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = item.food.name,
                style = MaterialTheme.typography.body1
            )
        }
        Text(
            text = "£${String.format("%.2f", item.food.price * item.quantity)}",
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun OrderSummary(order: Order) {
    Column {
        SummaryRow("Subtotal", order.subtotal)
        SummaryRow("Delivery Fee", order.deliveryFee)
        Spacer(modifier = Modifier.height(8.dp))
        SummaryRow("Total", order.total, true)
    }
}

@Composable
private fun SummaryRow(
    label: String,
    amount: Double,
    isTotal: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = if (isTotal) MaterialTheme.typography.h6 else MaterialTheme.typography.body1,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = "£${String.format("%.2f", amount)}",
            style = if (isTotal) MaterialTheme.typography.h6 else MaterialTheme.typography.body1,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun OrderStatusChip(status: OrderStatus) {
    Surface(
        color = when (status) {
            OrderStatus.PENDING -> MaterialTheme.colors.primary.copy(alpha = 0.1f)
            OrderStatus.PREPARING -> MaterialTheme.colors.secondary.copy(alpha = 0.1f)
            OrderStatus.DELIVERING -> MaterialTheme.colors.primaryVariant.copy(alpha = 0.1f)
            OrderStatus.DELIVERED -> MaterialTheme.colors.primary.copy(alpha = 0.1f)
            OrderStatus.CANCELLED -> MaterialTheme.colors.error.copy(alpha = 0.1f)
        },
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = when (status) {
                OrderStatus.PENDING -> MaterialTheme.colors.primary
                OrderStatus.PREPARING -> MaterialTheme.colors.secondary
                OrderStatus.DELIVERING -> MaterialTheme.colors.primaryVariant
                OrderStatus.DELIVERED -> MaterialTheme.colors.primary
                OrderStatus.CANCELLED -> MaterialTheme.colors.error
            },
            style = MaterialTheme.typography.caption
        )
    }
}

private fun formatDate(date: Date): String {
    return SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(date)
} 