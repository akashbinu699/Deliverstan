package com.example.delivaroos.api

import retrofit2.Response
import retrofit2.http.GET

interface FoodishApi {
    @GET("api/")
    suspend fun getRandomFood(): Response<FoodishResponse>
}

data class FoodishResponse(
    val image: String
) 