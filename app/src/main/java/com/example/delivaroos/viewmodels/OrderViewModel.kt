package com.example.delivaroos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivaroos.models.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*

open class OrderViewModel : ViewModel() {
    protected val _orders = MutableLiveData<List<Order>>(emptyList())
    val orders: LiveData<List<Order>> = _orders

    protected val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    protected val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    
    init {
        fetchOrders()
    }

    fun fetchOrders() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val snapshot = db.collection("orders")
                    .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                    .get()
                    .await()

                val ordersList = snapshot.documents.mapNotNull { document ->
                    try {
                        val data = document.data ?: return@mapNotNull null
                        
                        Order(
                            id = document.id,
                            userId = auth.currentUser?.uid ?: "",
                            items = (data["items"] as List<Map<String, Any>>).map { item ->
                                CartItem(
                                    food = Food(
                                        id = item["foodId"] as String,
                                        name = item["name"] as String,
                                        price = (item["price"] as Number).toDouble(),
                                        imageUrl = item["imageUrl"] as String,
                                        category = item["category"] as String,
                                        description = item["description"] as String
                                    ),
                                    quantity = (item["quantity"] as Number).toInt()
                                )
                            },
                            status = OrderStatus.valueOf(data["status"] as String),
                            orderDate = (data["timestamp"] as com.google.firebase.Timestamp).toDate(),
                            deliveryAddress = "",  // Not used in this version
                            subtotal = (data["totalAmount"] as Number).toDouble(),
                            deliveryFee = 0.0,  // Not used in this version
                            total = (data["totalAmount"] as Number).toDouble()
                        )
                    } catch (e: Exception) {
                        null
                    }
                }

                _orders.value = ordersList
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun getOrder(orderId: String): LiveData<Order> {
        val orderData = MutableLiveData<Order>()
        
        viewModelScope.launch {
            try {
                val document = db.collection("orders")
                    .document(orderId)
                    .get()
                    .await()

                val data = document.data
                if (data != null) {
                    orderData.value = Order(
                        id = document.id,
                        userId = auth.currentUser?.uid ?: "",
                        items = (data["items"] as List<Map<String, Any>>).map { item ->
                            CartItem(
                                food = Food(
                                    id = item["foodId"] as String,
                                    name = item["name"] as String,
                                    price = (item["price"] as Number).toDouble(),
                                    imageUrl = item["imageUrl"] as String,
                                    category = item["category"] as String,
                                    description = item["description"] as String
                                ),
                                quantity = (item["quantity"] as Number).toInt()
                            )
                        },
                        status = OrderStatus.valueOf(data["status"] as String),
                        orderDate = (data["timestamp"] as com.google.firebase.Timestamp).toDate(),
                        deliveryAddress = "",  // Not used in this version
                        subtotal = (data["totalAmount"] as Number).toDouble(),
                        deliveryFee = 0.0,  // Not used in this version
                        total = (data["totalAmount"] as Number).toDouble()
                    )
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
        
        return orderData
    }

    fun placeOrder(cartItems: List<CartItem>) {
        viewModelScope.launch {
            try {
                val orderItems = cartItems.map { cartItem ->
                    mapOf(
                        "foodId" to cartItem.food.id,
                        "name" to cartItem.food.name,
                        "price" to cartItem.food.price,
                        "imageUrl" to cartItem.food.imageUrl,
                        "category" to cartItem.food.category,
                        "description" to cartItem.food.description,
                        "quantity" to cartItem.quantity
                    )
                }

                val totalAmount = cartItems.sumOf { it.food.price * it.quantity }
                val orderId = UUID.randomUUID().toString()

                val orderData = hashMapOf(
                    "orderId" to orderId,
                    "items" to orderItems,
                    "totalAmount" to totalAmount,
                    "timestamp" to Date(),
                    "status" to "PENDING"
                )

                db.collection("orders")
                    .add(orderData)
                    .await()

                fetchOrders() // Refresh orders list
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
} 