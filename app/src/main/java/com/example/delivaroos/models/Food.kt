package com.example.delivaroos.models

import com.google.firebase.firestore.DocumentId

data class Food(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val category: String = ""
) {
    val formattedPrice: String
        get() = "£%.2f".format(price)

    fun copy(imageUrl: String) = Food(
        id = id,
        name = name,
        description = description,
        price = price,
        imageUrl = imageUrl,
        category = category
    )
} 