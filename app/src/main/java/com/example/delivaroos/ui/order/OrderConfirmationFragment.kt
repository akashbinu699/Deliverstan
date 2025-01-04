package com.example.delivaroos.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.delivaroos.R
import com.example.delivaroos.databinding.FragmentOrderConfirmationBinding
import com.example.delivaroos.models.Order
import com.example.delivaroos.models.OrderStatus
import com.example.delivaroos.viewmodels.CartViewModel
import com.example.delivaroos.viewmodels.OrderViewModel
import java.util.UUID

class OrderConfirmationFragment : Fragment() {
    private var _binding: FragmentOrderConfirmationBinding? = null
    private val binding get() = _binding!!
    private val orderViewModel: OrderViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create new order from cart items
        cartViewModel.cartItems.value?.let { items ->
            if (items.isNotEmpty()) {
                val orderId = UUID.randomUUID().toString()
                val order = Order(
                    id = orderId,
                    items = items.toList(), // Create a new list to avoid reference issues
                    totalAmount = cartViewModel.totalPrice.value ?: 0.0,
                    status = OrderStatus.PENDING,
                    timestamp = System.currentTimeMillis()
                )
                
                // Add order and show confirmation
                orderViewModel.addOrder(order)
                binding.textOrderNumber.text = getString(R.string.order_number, orderId.takeLast(6))
                
                // Clear cart after successful order creation
                cartViewModel.clearCart()
            }
        }

        binding.buttonBackToHome.setOnClickListener {
            findNavController().navigate(
                R.id.action_orderConfirmationFragment_to_navigation_home,
                null,
                null
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 