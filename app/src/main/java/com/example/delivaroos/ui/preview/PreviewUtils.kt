package com.example.delivaroos.ui.preview

import android.app.Application
import com.example.delivaroos.models.OrderStatus
import com.example.delivaroos.viewmodels.*

class PreviewFoodViewModel : FoodViewModel() {
    init {
        _foodItems.value = listOf(
            PreviewData.sampleFood,
            PreviewData.sampleFood.copy(id = "2", name = "Veggie Burger"),
            PreviewData.sampleFood.copy(id = "3", name = "Fish Burger")
        )
        _isLoading.value = false
    }
}

class PreviewCartViewModel : CartViewModel() {
    init {
        _cartItems.value = listOf(
            PreviewData.sampleCartItem,
            PreviewData.sampleCartItem.copy(
                food = PreviewData.sampleFood.copy(id = "2", name = "Veggie Burger")
            )
        )
    }
}

class PreviewOrderViewModel : OrderViewModel() {
    init {
        _orders.value = listOf(
            PreviewData.sampleOrder,
            PreviewData.sampleOrder.copy(
                id = "order456",
                status = OrderStatus.DELIVERED
            )
        )
        _isLoading.value = false
    }
}

class PreviewUserViewModel(application: Application) : UserViewModel(application) {
    init {
        _userName.value = "John Doe"
        _userEmail.value = "john@example.com"
        _isLoggedIn.value = true
    }
} 