package com.example.delivaroos.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.delivaroos.databinding.ItemCartBinding
import com.example.delivaroos.models.CartItem

class CartAdapter(
    private val onItemClick: (CartItem) -> Unit,
    private val onIncrement: (CartItem) -> Unit,
    private val onDecrement: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var items = listOf<CartItem>()

    fun submitList(newItems: List<CartItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class CartViewHolder(
        private val binding: ItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.apply {
                foodImage.load(item.food.imageUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                foodName.text = item.food.name
                foodPrice.text = "£${String.format("%.2f", item.food.price)}"
                quantity.text = item.quantity.toString()

                root.setOnClickListener { onItemClick(item) }
                incrementButton.setOnClickListener { onIncrement(item) }
                decrementButton.setOnClickListener { onDecrement(item) }
            }
        }
    }
} 