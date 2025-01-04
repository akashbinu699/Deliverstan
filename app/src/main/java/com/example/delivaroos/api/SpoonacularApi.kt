package com.example.delivaroos.api

import com.example.delivaroos.models.MenuItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SpoonacularApi {
    @GET("food/menuItems/search")
    suspend fun searchMenuItems(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("number") number: Int = 20
    ): Response<MenuItemsResponse>
} 