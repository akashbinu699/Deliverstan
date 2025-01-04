package com.example.delivaroos.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.delivaroos.databinding.FragmentHomeBinding
import com.example.delivaroos.models.CartItem
import com.example.delivaroos.models.FoodItem
import com.example.delivaroos.viewmodels.CartViewModel
import com.example.delivaroos.viewmodels.FoodViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val foodViewModel: FoodViewModel by viewModels()
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
        
        loadFoodItems()
    }

    private fun setupRecyclerView() {
        foodAdapter = FoodAdapter { foodItem ->
            cartViewModel.addToCart(CartItem(foodItem))
            Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
        }
        
        binding.recyclerViewFood.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = foodAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        foodViewModel.foodItems.observe(viewLifecycleOwner) { items ->
            foodAdapter.submitList(items)
            binding.progressBar.visibility = View.GONE
            binding.textEmpty.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }

        foodViewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            binding.progressBar.visibility = View.GONE
            binding.textEmpty.visibility = View.VISIBLE
            binding.textEmpty.text = "Error loading food items"
        }

        foodViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.swipeRefresh.isRefreshing = isLoading
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            loadFoodItems()
        }
    }

    private fun loadFoodItems() {
        binding.progressBar.visibility = View.VISIBLE
        foodViewModel.searchFoodItems("burger,pizza,pasta,sushi,salad")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}