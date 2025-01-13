package com.example.delivaroos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivaroos.models.CartItem
import com.example.delivaroos.models.Food
import kotlinx.coroutines.launch

open class CartViewModel : ViewModel() {
    protected val _cartItems = MutableLiveData<List<CartItem>>(emptyList())
    val cartItems: LiveData<List<CartItem>> = _cartItems

    protected val _totalAmount = MutableLiveData(0.0)
    val totalAmount: LiveData<Double> = _totalAmount

    protected val _orderPlaced = MutableLiveData<String?>(null)
    val orderPlaced: LiveData<String?> = _orderPlaced

    fun addToCart(food: Food) {
        val currentItems = _cartItems.value ?: emptyList()
        val existingItem = currentItems.find { it.food.id == food.id }

        val updatedItems = if (existingItem != null) {
            currentItems.map {
                if (it.food.id == food.id) {
                    it.copy(quantity = it.quantity + 1)
                } else it
            }
        } else {
            currentItems + CartItem(food = food, quantity = 1)
        }

        _cartItems.value = updatedItems
        updateTotalAmount()
    }

    fun removeFromCart(food: Food) {
        val currentItems = _cartItems.value ?: emptyList()
        val existingItem = currentItems.find { it.food.id == food.id }

        if (existingItem != null) {
            val updatedItems = if (existingItem.quantity > 1) {
                currentItems.map {
                    if (it.food.id == food.id) {
                        it.copy(quantity = it.quantity - 1)
                    } else it
                }
            } else {
                currentItems.filter { it.food.id != food.id }
            }

            _cartItems.value = updatedItems
            updateTotalAmount()
        }
    }

    private fun updateTotalAmount() {
        val total = _cartItems.value?.sumOf { it.food.price * it.quantity } ?: 0.0
        _totalAmount.value = total
    }

    fun placeOrder(orderViewModel: OrderViewModel) {
        viewModelScope.launch {
            try {
                val currentItems = _cartItems.value ?: emptyList()
                if (currentItems.isNotEmpty()) {
                    orderViewModel.placeOrder(currentItems)
                    // Clear cart after successful order
                    _cartItems.value = emptyList()
                    updateTotalAmount()
                    _orderPlaced.value = "Order placed successfully!"
                }
            } catch (e: Exception) {
                _orderPlaced.value = "Failed to place order: ${e.message}"
            }
        }
    }

    fun clearOrderPlaced() {
        _orderPlaced.value = null
    }
} 