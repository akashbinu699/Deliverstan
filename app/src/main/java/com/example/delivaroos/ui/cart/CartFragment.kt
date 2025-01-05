package com.example.delivaroos.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.delivaroos.R
import com.example.delivaroos.databinding.FragmentCartBinding
import com.example.delivaroos.viewmodels.CartViewModel

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by activityViewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupCheckoutButton()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onQuantityChange = { item, newQuantity ->
                if (newQuantity == 0) {
                    cartViewModel.removeFromCart(item)
                } else {
                    cartViewModel.updateQuantity(item, newQuantity)
                }
            }
        )
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
    }

    private fun setupObservers() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.submitList(items)
            updateVisibility(items.isEmpty())
        }

        cartViewModel.totalPrice.observe(viewLifecycleOwner) { total ->
            binding.textTotalAmount.text = String.format("£%.2f", total)
            binding.buttonCheckout.isEnabled = total > 0
        }
    }

    private fun setupCheckoutButton() {
        binding.buttonCheckout.setOnClickListener {
            if (cartViewModel.cartItems.value?.isNotEmpty() == true) {
                findNavController().navigate(R.id.action_navigation_cart_to_orderConfirmationFragment)
            }
        }
    }

    private fun updateVisibility(isEmpty: Boolean) {
        binding.apply {
            emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
            cartContent.visibility = if (isEmpty) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 