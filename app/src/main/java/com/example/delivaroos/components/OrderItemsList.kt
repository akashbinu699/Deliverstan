package com.example.delivaroos.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.delivaroos.models.CartItem

@Composable
fun OrderItemsList(
    items: List<CartItem>,
    totalAmount: Double,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(items) { item ->
                OrderItem(item)
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Amount",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "₹${String.format("%.2f", totalAmount)}",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun OrderItem(
    item: CartItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = item.food.name,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "₹${item.food.price} × ${item.quantity}",
                style = MaterialTheme.typography.body2
            )
        }
        Text(
            text = "₹${String.format("%.2f", item.food.price * item.quantity)}",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Medium
        )
    }
} 