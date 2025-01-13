package com.example.delivaroos.data.repository

import android.util.Log
import com.example.delivaroos.data.api.FoodishApi
import com.example.delivaroos.models.Food
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class FoodRepository {
    private val api = Retrofit.Builder()
        .baseUrl(FoodishApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FoodishApi::class.java)

    private val foodNames = mapOf(
        "biryani" to "Chicken Biryani",
        "burger" to "Classic Burger",
        "butter-chicken" to "Butter Chicken",
        "dessert" to "Sweet Dessert",
        "dosa" to "Masala Dosa",
        "pasta" to "Italian Pasta",
        "pizza" to "Pepperoni Pizza",
        "rice" to "Fried Rice",
        "samosa" to "Veg Samosa"
    )

    private val foodDescriptions = mapOf(
        "biryani" to "Aromatic rice dish with spices and tender chicken",
        "burger" to "Juicy patty with fresh vegetables and special sauce",
        "butter-chicken" to "Creamy and rich Indian curry with tender chicken",
        "dessert" to "Sweet and delightful dessert to satisfy your cravings",
        "dosa" to "Crispy South Indian crepe served with chutney",
        "pasta" to "Classic Italian pasta with rich tomato sauce",
        "pizza" to "Hand-tossed pizza with fresh toppings",
        "rice" to "Flavorful fried rice with vegetables",
        "samosa" to "Crispy pastry filled with spiced potatoes"
    )

    suspend fun getFoods(): List<Food> {
        val foods = mutableListOf<Food>()
        
        repeat(10) { index ->
            try {
                Log.d("FoodRepository", "Fetching random food")
                val response = api.getRandomFood()
                
                if (response.isSuccessful) {
                    val imageUrl = response.body()?.image
                    if (imageUrl != null) {
                        // Extract category from image URL
                        val category = imageUrl.substringAfter("/images/").substringBefore("/")
                        
                        foods.add(
                            Food(
                                id = index.toString(),
                                name = foodNames[category] ?: "Unknown Food",
                                description = foodDescriptions[category] ?: "Delicious food item",
                                price = generateRandomPrice(),
                                imageUrl = imageUrl,
                                category = category
                            )
                        )
                        Log.d("FoodRepository", "Added food from category: $category")
                    }
                } else {
                    Log.e("FoodRepository", "Error response: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("FoodRepository", "Error fetching food", e)
            }
        }
        
        Log.d("FoodRepository", "Total foods fetched: ${foods.size}")
        return foods
    }

    private fun generateRandomPrice(): Double {
        return (Random.nextDouble(100.0, 500.0) * 100).toInt() / 100.0
    }
} 