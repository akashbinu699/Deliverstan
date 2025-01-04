package com.example.delivaroos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.delivaroos.models.CartItem
import com.example.delivaroos.models.FoodItem

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        _cartItems.value = emptyList()
        updateTotalPrice()
    }

    fun addToCart(foodItem: FoodItem) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        val existingItem = currentItems.find { it.foodItem.id == foodItem.id }

        if (existingItem != null) {
            val index = currentItems.indexOf(existingItem)
            currentItems[index] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            currentItems.add(CartItem(foodItem, 1))
        }

        _cartItems.value = currentItems
        updateTotalPrice()
    }

    fun updateQuantity(cartItem: CartItem, quantity: Int) {
        val currentItems = _cartItems.value?.toMutableList() ?: return
        val index = currentItems.indexOfFirst { it.foodItem.id == cartItem.foodItem.id }
        if (index != -1) {
            currentItems[index] = cartItem.copy(quantity = quantity)
            _cartItems.value = currentItems
            updateTotalPrice()
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        val currentItems = _cartItems.value?.toMutableList() ?: return
        currentItems.removeAll { it.foodItem.id == cartItem.foodItem.id }
        _cartItems.value = currentItems
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val total = _cartItems.value?.sumOf { it.totalPrice } ?: 0.0
        _totalPrice.value = total
    }

    fun clearCart() {
        _cartItems.value = emptyList()
        updateTotalPrice()
    }
} 