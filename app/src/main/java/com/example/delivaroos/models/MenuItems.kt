package com.example.delivaroos.models

import com.google.gson.annotations.SerializedName

data class MenuItemsResponse(
    @SerializedName("menuItems")
    val menuItems: List<MenuItem>,
    @SerializedName("totalMenuItems")
    val totalMenuItems: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("number")
    val number: Int
)

data class MenuItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("restaurantChain")
    val restaurantChain: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("imageType")
    val imageType: String,
    @SerializedName("servingSize")
    val servingSize: String? = null,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int = 15,
    @SerializedName("price")
    val price: Double? = null
) {
    val pricePerServing: Double
        get() = price ?: 9.99 // Default price if not provided
} 