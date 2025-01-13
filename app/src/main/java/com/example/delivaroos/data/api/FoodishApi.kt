package com.example.delivaroos.data.api

import com.example.delivaroos.data.model.FoodishResponse
import retrofit2.Response
import retrofit2.http.GET

interface FoodishApi {
    @GET("api")
    suspend fun getRandomFood(): Response<FoodishResponse>

    companion object {
        const val BASE_URL = "https://foodish-api.com/"
        
        val categories = listOf(
            "biryani",
            "burger",
            "butter-chicken",
            "dessert",
            "dosa",
            "pasta",
            "pizza",
            "rice",
            "samosa"
        )
    }
} 