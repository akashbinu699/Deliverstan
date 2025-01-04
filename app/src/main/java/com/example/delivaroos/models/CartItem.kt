package com.example.delivaroos.models

data class CartItem(
    val foodItem: FoodItem,
    var quantity: Int = 1,
    var specialInstructions: String = ""
) {
    val totalPrice: Double
        get() = foodItem.price * quantity
} 