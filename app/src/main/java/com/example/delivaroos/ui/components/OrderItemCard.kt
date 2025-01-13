package com.example.delivaroos.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.delivaroos.models.CartItem

@Composable
fun OrderItemCard(
    cartItem: CartItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = cartItem.food.imageUrl,
                contentDescription = cartItem.food.name,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = cartItem.food.name,
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = "£${String.format("%.2f", cartItem.food.price)}",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.primary
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Qty: ${cartItem.quantity}",
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = "£${String.format("%.2f", cartItem.food.price * cartItem.quantity)}",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
} 