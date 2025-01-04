package com.example.delivaroos.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.delivaroos.R
import com.example.delivaroos.databinding.ItemOrderDetailBinding
import com.example.delivaroos.models.CartItem

class OrderItemsAdapter : ListAdapter<CartItem, OrderItemsAdapter.OrderItemViewHolder>(OrderItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        return OrderItemViewHolder(
            ItemOrderDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OrderItemViewHolder(
        private val binding: ItemOrderDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.apply {
                textTitle.text = item.foodItem.title
                textQuantity.text = "${item.quantity}x"
                textPrice.text = String.format("£%.2f", item.totalPrice)
                textRestaurant.text = item.foodItem.restaurantChain

                Glide.with(root)
                    .load(item.foodItem.image)
                    .placeholder(R.drawable.placeholder_food)
                    .error(R.drawable.placeholder_food)
                    .into(imageFood)
            }
        }
    }

    private class OrderItemDiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem) =
            oldItem.foodItem.id == newItem.foodItem.id

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem) =
            oldItem == newItem
    }
} 