package com.example.delivaroos.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.delivaroos.R
import com.example.delivaroos.databinding.ItemCartBinding
import com.example.delivaroos.models.CartItem

class CartAdapter(
    private val onQuantityChange: (CartItem, Int) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CartViewHolder(
        private val binding: ItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.apply {
                textTitle.text = item.foodItem.title
                textPrice.text = String.format("£%.2f", item.totalPrice)
                textQuantity.text = item.quantity.toString()
                textRestaurant.text = item.foodItem.restaurantChain

                Glide.with(root)
                    .load(item.foodItem.image)
                    .placeholder(R.drawable.placeholder_food)
                    .error(R.drawable.placeholder_food)
                    .into(imageFood)

                buttonMinus.setOnClickListener {
                    if (item.quantity > 0) {
                        onQuantityChange(item, item.quantity - 1)
                    }
                }

                buttonPlus.setOnClickListener {
                    onQuantityChange(item, item.quantity + 1)
                }
            }
        }
    }

    private class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem) =
            oldItem.foodItem.id == newItem.foodItem.id

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem) =
            oldItem == newItem
    }
} 