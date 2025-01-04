package com.example.delivaroos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivaroos.api.RetrofitClient
import com.example.delivaroos.models.FoodItem
import kotlinx.coroutines.launch
import kotlin.random.Random

class FoodViewModel : ViewModel() {
    private val _foodItems = MutableLiveData<List<FoodItem>>()
    val foodItems: LiveData<List<FoodItem>> = _foodItems

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val foodCategories = mapOf(
        "biryani" to listOf(
            "Chicken Biryani",
            "Mutton Biryani",
            "Vegetable Biryani",
            "Hyderabadi Biryani",
            "Special Biryani"
        ),
        "burger" to listOf(
            "Classic Cheeseburger",
            "Chicken Burger",
            "BBQ Bacon Burger",
            "Veggie Burger",
            "Double Beef Burger"
        ),
        "butter-chicken" to listOf(
            "Butter Chicken",
            "Creamy Butter Chicken",
            "Spicy Butter Chicken",
            "Traditional Butter Chicken",
            "Royal Butter Chicken"
        ),
        "dessert" to listOf(
            "Chocolate Cake",
            "Ice Cream Sundae",
            "Cheesecake",
            "Apple Pie",
            "Tiramisu"
        ),
        "dosa" to listOf(
            "Masala Dosa",
            "Plain Dosa",
            "Cheese Dosa",
            "Onion Dosa",
            "Mysore Masala Dosa"
        ),
        "idly" to listOf(
            "Plain Idli",
            "Rava Idli",
            "Mini Idli Sambar",
            "Masala Idli",
            "Idli Platter"
        ),
        "pasta" to listOf(
            "Spaghetti Carbonara",
            "Penne Arrabbiata",
            "Fettuccine Alfredo",
            "Pasta Bolognese",
            "Creamy Mushroom Pasta"
        ),
        "pizza" to listOf(
            "Margherita Pizza",
            "Pepperoni Pizza",
            "BBQ Chicken Pizza",
            "Vegetarian Supreme",
            "Four Cheese Pizza"
        ),
        "rice" to listOf(
            "Fried Rice",
            "Steamed Rice",
            "Jeera Rice",
            "Coconut Rice",
            "Vegetable Pulao"
        ),
        "samosa" to listOf(
            "Vegetable Samosa",
            "Keema Samosa",
            "Cheese Samosa",
            "Punjabi Samosa",
            "Mini Samosa Platter"
        )
    )

    private val restaurants = listOf(
        "Royal Kitchen",
        "Spice Garden",
        "Food Paradise",
        "Tasty Bites",
        "Foodie's Corner",
        "The Grand Kitchen",
        "Flavor House",
        "Gourmet Express",
        "Chef's Special",
        "Urban Eats"
    )

    fun searchFoodItems() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val items = mutableListOf<FoodItem>()
                repeat(10) {
                    try {
                        val response = RetrofitClient.foodishApi.getRandomFood()
                        if (response.isSuccessful) {
                            val imageUrl = response.body()?.image ?: ""
                            // Extract category from image URL
                            val category = imageUrl.substringAfter("/images/").substringBefore("/")
                            val names = foodCategories[category] ?: foodCategories["burger"]!!
                            
                            items.add(
                                FoodItem(
                                    id = it,
                                    title = names.random(),
                                    image = imageUrl,
                                    restaurantChain = restaurants.random(),
                                    price = (2.99 + (Random.nextDouble() * 2)).coerceAtMost(4.99)
                                )
                            )
                        }
                    } catch (e: Exception) {
                        // Continue with next item if one fails
                    }
                }
                _foodItems.value = items
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
} 