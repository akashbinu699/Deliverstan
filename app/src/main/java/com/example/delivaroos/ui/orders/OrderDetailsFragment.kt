package com.example.delivaroos.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.delivaroos.databinding.FragmentOrderDetailsBinding
import com.example.delivaroos.viewmodels.OrderViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderDetailsFragment : Fragment() {
    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!
    private val orderViewModel: OrderViewModel by activityViewModels()
    private val args: OrderDetailsFragmentArgs by navArgs()
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    private lateinit var orderItemsAdapter: OrderItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadOrderDetails()
    }

    private fun setupRecyclerView() {
        orderItemsAdapter = OrderItemsAdapter()
        binding.recyclerViewItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderItemsAdapter
            setHasFixedSize(true)
        }
    }

    private fun loadOrderDetails() {
        orderViewModel.getOrderById(args.orderId)?.let { order ->
            binding.apply {
                // Order header
                textOrderId.text = "Order #${order.id.takeLast(6)}"
                textOrderDate.text = dateFormat.format(Date(order.timestamp))
                textOrderStatus.text = order.status.name

                // Order items
                orderItemsAdapter.submitList(order.items)

                // Order summary
                textSubtotal.text = String.format("£%.2f", order.totalAmount)
                textDeliveryFee.text = "£2.99"
                val total = order.totalAmount + 2.99
                textTotal.text = String.format("£%.2f", total)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 