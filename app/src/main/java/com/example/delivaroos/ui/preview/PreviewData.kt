package com.example.delivaroos.ui.preview

import com.example.delivaroos.models.*
import java.util.*

object PreviewData {
    val sampleFood = Food(
        id = "1",
        name = "Chicken Burger",
        description = "Delicious chicken burger with fresh vegetables",
        price = 9.99,
        imageUrl = "",
        category = "Burgers"
    )

    val sampleCartItem = CartItem(
        food = sampleFood,
        quantity = 2
    )

    val sampleOrder = Order(
        id = "order123",
        userId = "user123",
        items = listOf(sampleCartItem),
        status = OrderStatus.PENDING,
        orderDate = Date(),
        deliveryAddress = "123 Main St",
        subtotal = 19.98,
        deliveryFee = 2.99,
        total = 22.97
    )
} 