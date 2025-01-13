package com.example.delivaroos.data.api

import com.example.delivaroos.data.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    @GET("api/json/v1/1/search.php")
    suspend fun searchMeals(@Query("s") query: String = ""): MealResponse

    @GET("api/json/v1/1/filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealResponse

    companion object {
        const val BASE_URL = "https://www.themealdb.com/api/"
    }
} 