package com.example.delivaroos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.delivaroos.models.CartItem

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<List<CartItem>>(emptyList())
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _totalPrice = MutableLiveData(0.0)
    val totalPrice: LiveData<Double> = _totalPrice

    fun addToCart(cartItem: CartItem) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        val existingItem = currentItems.find { it.foodItem.id == cartItem.foodItem.id }
        
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            currentItems.add(cartItem)
        }
        
        _cartItems.value = currentItems
        updateTotalPrice()
    }

    fun removeFromCart(cartItem: CartItem) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        currentItems.removeAll { it.foodItem.id == cartItem.foodItem.id }
        _cartItems.value = currentItems
        updateTotalPrice()
    }

    fun updateQuantity(cartItem: CartItem, quantity: Int) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        currentItems.find { it.foodItem.id == cartItem.foodItem.id }?.quantity = quantity
        _cartItems.value = currentItems
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        _totalPrice.value = _cartItems.value?.sumOf { it.totalPrice } ?: 0.0
    }

    fun clearCart() {
        _cartItems.value = emptyList()
        updateTotalPrice()
    }
} 