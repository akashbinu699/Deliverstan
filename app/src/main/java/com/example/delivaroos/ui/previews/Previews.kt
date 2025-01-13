package com.example.delivaroos.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.delivaroos.models.Food
import com.example.delivaroos.models.CartItem
import com.example.delivaroos.ui.components.FoodItem
import com.example.delivaroos.ui.components.CartItemCard
import com.example.delivaroos.ui.theme.DelivaroosTheme

@Preview(showBackground = true)
@Composable
fun FoodItemPreview() {
    DelivaroosTheme {
        FoodItem(
            food = Food(
                id = "1",
                name = "Veg Samosa",
                price = 4.99,
                imageUrl = "",
                category = "samosa",
                description = "Delicious Veg Samosa from our samosa selection"
            ),
            onAddToCart = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    DelivaroosTheme {
        CartItemCard(
            cartItem = CartItem(
                food = Food(
                    id = "1",
                    name = "Veg Samosa",
                    price = 4.99,
                    imageUrl = "",
                    category = "samosa",
                    description = "Delicious Veg Samosa"
                ),
                quantity = 2
            ),
            onIncrement = {},
            onDecrement = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun DarkThemePreview() {
    DelivaroosTheme(darkTheme = true) {
        FoodItem(
            food = Food(
                id = "1",
                name = "Veg Samosa",
                price = 4.99,
                imageUrl = "",
                category = "samosa",
                description = "Delicious Veg Samosa from our samosa selection"
            ),
            onAddToCart = {}
        )
    }
} 