package com.example.delivaroos.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private val viewModel: CartViewModel by activityViewModels()
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
            onQuantityChanged = { cartItem, quantity ->
                viewModel.updateQuantity(cartItem, quantity)
            },
            onItemRemoved = { cartItem ->
                viewModel.removeFromCart(cartItem)
                Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show()
            }
        )
        
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
    }

    private fun setupObservers() {
        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.submitList(items)
            binding.emptyStateGroup.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
            binding.cartContentGroup.visibility = if (items.isNotEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) { total ->
            binding.textTotalPrice.text = String.format("£%.2f", total)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupCheckoutButton() {
        binding.buttonCheckout.setOnClickListener {
            if (viewModel.cartItems.value?.isNotEmpty() == true) {
                viewModel.clearCart()
                findNavController().navigate(R.id.action_navigation_cart_to_orderConfirmationFragment)
            } else {
                Toast.makeText(context, "Your cart is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 