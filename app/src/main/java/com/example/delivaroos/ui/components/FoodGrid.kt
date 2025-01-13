package com.example.delivaroos.ui.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.delivaroos.models.Food

@Composable
fun FoodGrid(
    foods: List<Food>,
    onAddToCart: (Food) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(foods) { food ->
            FoodItem(
                food = food,
                onAddToCart = { onAddToCart(food) }
            )
        }
    }
} 