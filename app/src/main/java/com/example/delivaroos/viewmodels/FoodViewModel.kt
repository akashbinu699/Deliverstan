package com.example.delivaroos.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivaroos.data.api.FoodishApi
import com.example.delivaroos.models.Food
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

open class FoodViewModel : ViewModel() {
    protected val _foodItems = MutableLiveData<List<Food>>(emptyList())
    val foodItems: LiveData<List<Food>> = _foodItems

    protected val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    protected val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private val api = Retrofit.Builder()
        .baseUrl(FoodishApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FoodishApi::class.java)

    private val foodCategories = mapOf(
        "biryani" to listOf("Chicken Biryani", "Mutton Biryani", "Veg Biryani"),
        "burger" to listOf("Classic Burger", "Cheese Burger", "Veggie Burger"),
        "butter-chicken" to listOf("Butter Chicken", "Chicken Tikka Masala"),
        "dessert" to listOf("Chocolate Cake", "Ice Cream", "Cheesecake"),
        "dosa" to listOf("Masala Dosa", "Plain Dosa", "Onion Dosa"),
        "pasta" to listOf("Spaghetti", "Penne Arrabiata", "Fettuccine Alfredo"),
        "pizza" to listOf("Margherita Pizza", "Pepperoni Pizza", "Veggie Pizza"),
        "rice" to listOf("Fried Rice", "Steamed Rice", "Jeera Rice"),
        "samosa" to listOf("Veg Samosa", "Chicken Samosa", "Paneer Samosa")
    )

    init {
        loadFoodItems()
    }

    fun loadFoodItems() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val items = mutableListOf<Food>()
                repeat(10) { index ->
                    try {
                        Log.d("FoodViewModel", "Fetching random food")
                        val response = api.getRandomFood()
                        if (response.isSuccessful) {
                            val imageUrl = response.body()?.image
                            Log.d("FoodViewModel", "Response received: $imageUrl")
                            
                            if (imageUrl != null) {
                                val category = imageUrl.substringAfter("/images/").substringBefore("/")
                                val names = foodCategories[category] ?: foodCategories["burger"]!!
                                val name = names.random()
                                
                                // Generate a random price between £2.99 and £4.98
                                val price = (Random.nextDouble(2.99, 4.98) * 100).toInt() / 100.0
                                
                                items.add(
                                    Food(
                                        id = "$index",
                                        name = name,
                                        price = price,
                                        imageUrl = imageUrl,
                                        category = category,
                                        description = "Delicious $name from our $category selection"
                                    )
                                )
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("FoodViewModel", "Error fetching food", e)
                    }
                }
                _foodItems.value = items
            } catch (e: Exception) {
                _error.value = "Failed to load food items"
                Log.e("FoodViewModel", "Error loading food items", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
} 