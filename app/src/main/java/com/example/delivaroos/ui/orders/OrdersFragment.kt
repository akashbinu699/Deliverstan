package com.example.delivaroos.ui.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.delivaroos.databinding.FragmentOrdersBinding
import com.example.delivaroos.viewmodels.OrderViewModel

class OrdersFragment : Fragment() {
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private val orderViewModel: OrderViewModel by activityViewModels()
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter(
            onOrderClick = { order ->
                findNavController().navigate(
                    OrdersFragmentDirections.actionNavigationOrdersToOrderDetails(order.id)
                )
            }
        )
        binding.recyclerViewOrders.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        orderViewModel.orders.observe(viewLifecycleOwner) { orders ->
            Log.d("OrdersFragment", "Received ${orders.size} orders")
            orderAdapter.submitList(orders)
            updateVisibility(orders.isEmpty())
        }
    }

    private fun updateVisibility(isEmpty: Boolean) {
        binding.apply {
            emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
            recyclerViewOrders.visibility = if (isEmpty) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 