package com.example.delivaroos.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.delivaroos.databinding.FragmentHomeBinding
import com.example.delivaroos.viewmodels.CartViewModel
import com.example.delivaroos.viewmodels.FoodViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val foodViewModel: FoodViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupSwipeRefresh()

        if (foodViewModel.foodItems.value.isNullOrEmpty()) {
            foodViewModel.searchFoodItems()
        }
    }

    private fun setupRecyclerView() {
        foodAdapter = FoodAdapter(
            onAddToCart = { foodItem ->
                cartViewModel.addToCart(foodItem)
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
            }
        )

        binding.recyclerViewFood.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = foodAdapter
        }
    }

    private fun setupObservers() {
        foodViewModel.foodItems.observe(viewLifecycleOwner) { items ->
            foodAdapter.submitList(items)
            binding.emptyState.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }

        foodViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
        }

        foodViewModel.error.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            foodViewModel.searchFoodItems()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}