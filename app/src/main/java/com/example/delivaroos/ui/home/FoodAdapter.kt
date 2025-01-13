package com.example.delivaroos.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.delivaroos.databinding.ItemFoodBinding
import com.example.delivaroos.models.Food

class FoodAdapter(
    private val onItemClick: (Food) -> Unit,
    private val onAddToCart: (Food) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private var items = listOf<Food>()

    fun submitList(newItems: List<Food>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class FoodViewHolder(
        private val binding: ItemFoodBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(food: Food) {
            binding.apply {
                foodImage.load(food.imageUrl) {
                    crossfade(true)
                }
                foodName.text = food.name
                foodPrice.text = "£${String.format("%.2f", food.price)}"
                foodDescription.text = food.description

                root.setOnClickListener { onItemClick(food) }
                addToCartButton.setOnClickListener { onAddToCart(food) }
            }
        }
    }
} 