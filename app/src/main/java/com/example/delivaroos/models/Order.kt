package com.example.delivaroos.models

import java.util.*

data class Order(
    val id: String,
    val userId: String,
    val items: List<CartItem>,
    val status: OrderStatus,
    val orderDate: Date,
    val deliveryAddress: String,
    val subtotal: Double,
    val deliveryFee: Double,
    val total: Double,
    val deliveredAt: Date? = null
)

enum class OrderStatus {
    PENDING,
    PREPARING,
    DELIVERING,
    DELIVERED,
    CANCELLED
} 