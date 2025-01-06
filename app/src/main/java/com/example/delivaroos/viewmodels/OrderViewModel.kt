package com.example.delivaroos.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivaroos.models.CartItem
import com.example.delivaroos.models.FoodItem
import com.example.delivaroos.models.Order
import com.example.delivaroos.models.OrderStatus
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OrderViewModel : ViewModel() {
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders
    
    private val db = Firebase.firestore
    private val ordersCollection = db.collection("orders")

    init {
        _orders.value = emptyList()
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            try {
                val snapshot = ordersCollection
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .get()
                    .await()
                
                val ordersList = snapshot.documents.mapNotNull { doc ->
                    try {
                        val orderId = doc.getString("order_id") ?: return@mapNotNull null
                        val items = doc.get("order_items") as? List<Map<String, Any>> ?: return@mapNotNull null
                        val totalAmount = doc.getDouble("order_price") ?: return@mapNotNull null
                        val timestamp = doc.getLong("timestamp") ?: System.currentTimeMillis()
                        
                        val cartItems = items.map { item ->
                            CartItem(
                                foodItem = FoodItem(
                                    id = (item["id"] as? Number)?.toInt() ?: 0,
                                    title = item["title"] as? String ?: "",
                                    image = item["image"] as? String ?: "",
                                    restaurantChain = item["restaurantChain"] as? String ?: "",
                                    price = (item["price"] as? Number)?.toDouble() ?: 0.0
                                ),
                                quantity = (item["quantity"] as? Number)?.toInt() ?: 1
                            )
                        }

                        Order(
                            id = orderId,
                            items = cartItems,
                            totalAmount = totalAmount,
                            status = OrderStatus.valueOf(doc.getString("status") ?: OrderStatus.PENDING.name),
                            timestamp = timestamp
                        )
                    } catch (e: Exception) {
                        Log.e("OrderViewModel", "Error parsing order: ${e.message}")
                        null
                    }
                }
                
                _orders.value = ordersList
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Error loading orders: ${e.message}")
            }
        }
    }

    fun addOrder(order: Order) {
        viewModelScope.launch {
            try {
                val orderMap = hashMapOf(
                    "order_id" to order.id,
                    "order_items" to order.items.map { item ->
                        hashMapOf(
                            "id" to item.foodItem.id,
                            "title" to item.foodItem.title,
                            "image" to item.foodItem.image,
                            "restaurantChain" to item.foodItem.restaurantChain,
                            "price" to item.foodItem.price,
                            "quantity" to item.quantity
                        )
                    },
                    "order_price" to order.totalAmount,
                    "status" to order.status.name,
                    "timestamp" to order.timestamp
                )

                ordersCollection.document(order.id)
                    .set(orderMap)
                    .await()

                // Update local list
                val currentOrders = _orders.value?.toMutableList() ?: mutableListOf()
                currentOrders.add(0, order)
                _orders.value = currentOrders
                
                Log.d("OrderViewModel", "Order saved successfully: ${order.id}")
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Error saving order: ${e.message}")
            }
        }
    }

    fun getOrderById(orderId: String): Order? {
        return orders.value?.find { it.id == orderId }
    }

    fun updateOrderStatus(orderId: String, status: OrderStatus) {
        viewModelScope.launch {
            try {
                ordersCollection.document(orderId)
                    .update("status", status.name)
                    .await()

                // Update local list
                val currentOrders = _orders.value?.toMutableList() ?: mutableListOf()
                val orderIndex = currentOrders.indexOfFirst { it.id == orderId }
                if (orderIndex != -1) {
                    val updatedOrder = currentOrders[orderIndex].copy(status = status)
                    currentOrders[orderIndex] = updatedOrder
                    _orders.value = currentOrders
                }
                
                Log.d("OrderViewModel", "Order status updated: $orderId to $status")
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Error updating order status: ${e.message}")
            }
        }
    }
} 