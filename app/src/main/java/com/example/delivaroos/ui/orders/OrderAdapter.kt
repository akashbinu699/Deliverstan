package com.example.delivaroos.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.delivaroos.databinding.ItemOrderBinding
import com.example.delivaroos.models.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderAdapter(
    private val onOrderClick: (Order) -> Unit
) : ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffCallback()) {
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.root.setOnClickListener { onOrderClick(order) }
            binding.apply {
                textOrderId.text = "Order #${order.id.takeLast(6)}"
                textOrderStatus.text = order.status.name
                textOrderDate.text = dateFormat.format(Date(order.timestamp))
                textOrderAmount.text = String.format("£%.2f", order.totalAmount)
                
                val itemSummary = order.items.joinToString("\n") { 
                    "${it.quantity}x ${it.foodItem.title}"
                }
                textOrderItems.text = itemSummary
            }
        }
    }

    private class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Order, newItem: Order) =
            oldItem == newItem
    }
} 