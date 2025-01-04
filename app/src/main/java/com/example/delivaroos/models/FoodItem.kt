package com.example.delivaroos.models

data class FoodItem(
    val id: Int,
    val title: String,
    val image: String,
    val restaurantChain: String,
    val price: Double = 9.99
) 