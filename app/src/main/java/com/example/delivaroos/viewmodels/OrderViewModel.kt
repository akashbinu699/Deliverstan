package com.example.delivaroos.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.delivaroos.models.Order
import com.example.delivaroos.models.OrderStatus

class OrderViewModel : ViewModel() {
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    init {
        _orders.value = emptyList()
    }

    fun addOrder(order: Order) {
        val currentOrders = _orders.value?.toMutableList() ?: mutableListOf()
        currentOrders.add(0, order)
        _orders.value = currentOrders
        Log.d("OrderViewModel", "Added new order: ${order.id}, Total orders: ${currentOrders.size}")
    }

    fun getOrderById(orderId: String): Order? {
        return orders.value?.find { it.id == orderId }?.also {
            Log.d("OrderViewModel", "Found order: ${it.id}")
        }
    }

    fun updateOrderStatus(orderId: String, status: OrderStatus) {
        val currentOrders = _orders.value?.toMutableList() ?: mutableListOf()
        val orderIndex = currentOrders.indexOfFirst { it.id == orderId }
        if (orderIndex != -1) {
            val updatedOrder = currentOrders[orderIndex].copy(status = status)
            currentOrders[orderIndex] = updatedOrder
            _orders.value = currentOrders
            Log.d("OrderViewModel", "Updated order status: $orderId to $status")
        }
    }
} 