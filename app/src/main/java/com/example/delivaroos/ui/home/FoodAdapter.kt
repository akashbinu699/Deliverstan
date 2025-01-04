package com.example.delivaroos.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.delivaroos.R
import com.example.delivaroos.databinding.ItemFoodBinding
import com.example.delivaroos.models.FoodItem

class FoodAdapter(
    private val onAddToCart: (FoodItem) -> Unit
) : ListAdapter<FoodItem, FoodAdapter.FoodViewHolder>(FoodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            ItemFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FoodViewHolder(
        private val binding: ItemFoodBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(foodItem: FoodItem) {
            binding.apply {
                textTitle.text = foodItem.title
                textRestaurant.text = foodItem.restaurantChain
                textPrice.text = String.format("$%.2f", foodItem.price)

                Glide.with(root)
                    .load(foodItem.image)
                    .placeholder(R.drawable.placeholder_food)
                    .error(R.drawable.placeholder_food)
                    .into(imageFood)

                buttonAddToCart.setOnClickListener {
                    onAddToCart(foodItem)
                }
            }
        }
    }

    private class FoodDiffCallback : DiffUtil.ItemCallback<FoodItem>() {
        override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem) =
            oldItem == newItem
    }
} 