package com.example.delivaroos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivaroos.api.RetrofitClient
import com.example.delivaroos.models.FoodItem
import kotlinx.coroutines.launch

class FoodViewModel : ViewModel() {
    private val _foodItems = MutableLiveData<List<FoodItem>>()
    val foodItems: LiveData<List<FoodItem>> = _foodItems

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun searchFoodItems(query: String = "") {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = RetrofitClient.spoonacularApi.searchMenuItems(
                    RetrofitClient.API_KEY,
                    query
                )
                if (response.isSuccessful) {
                    _foodItems.value = response.body()?.menuItems?.map { menuItem ->
                        FoodItem(
                            id = menuItem.id,
                            title = menuItem.title,
                            image = menuItem.image,
                            restaurantChain = menuItem.restaurantChain,
                            price = menuItem.price ?: 9.99
                        )
                    } ?: emptyList()
                } else {
                    _error.value = "Failed to fetch food items"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
} 