package com.example.delivaroos.data.model

import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("meals")
    val meals: List<Meal>?
)

data class Meal(
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String,
    @SerializedName("strCategory")
    val strCategory: String? = "",
    @SerializedName("strArea")
    val strArea: String? = "",
    @SerializedName("strInstructions")
    val strInstructions: String? = ""
) 