package com.example.delivaroos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.delivaroos.models.Order
import com.example.delivaroos.models.OrderStatus
import com.example.delivaroos.navigation.NavScreen
import com.example.delivaroos.ui.components.ScreenLayout
import com.example.delivaroos.viewmodels.OrderViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.delivaroos.ui.theme.DelivaroosTheme
import com.example.delivaroos.ui.preview.PreviewData
import androidx.navigation.compose.rememberNavController
import com.example.delivaroos.ui.preview.PreviewOrderViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OrdersScreen(
    navController: NavController,
    orderViewModel: OrderViewModel = viewModel()
) {
    ScreenLayout(
        title = "My Orders",
        showBackButton = false
    ) { padding ->
        val orders by orderViewModel.orders.observeAsState(emptyList())
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = error ?: "An error occurred",
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { orderViewModel.fetchOrders() }) {
                            Text("Retry")
                        }
                    }
                }
                orders.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No orders yet")
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(orders) { order ->
                            OrderCard(
                                order = order,
                                onClick = {
                                    navController.navigate(NavScreen.OrderDetail.createRoute(order.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderCard(
    order: Order,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Order #${order.id.takeLast(6)}",
                    style = MaterialTheme.typography.h6
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "View Order",
                    tint = MaterialTheme.colors.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OrderStatusChip(status = order.status)
                Text(
                    text = formatDate(order.orderDate),
                    style = MaterialTheme.typography.caption
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${order.items.sumOf { it.quantity }} items",
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = "£${String.format("%.2f", order.total)}",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
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

@Preview(showBackground = true)
@Composable
fun OrdersScreenPreview() {
    DelivaroosTheme {
        OrdersScreen(
            navController = rememberNavController(),
            orderViewModel = PreviewOrderViewModel()
        )
    }
} 