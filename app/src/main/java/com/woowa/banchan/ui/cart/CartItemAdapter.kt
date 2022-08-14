package com.woowa.banchan.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemCartBinding

class CartItemAdapter: ListAdapter<TestCartItem, CartItemAdapter.CartItemViewHolder>(cartDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CartItemViewHolder(
            ItemCartBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CartItemViewHolder(
        private val binding: ItemCartBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TestCartItem) {
            itemView.setOnClickListener {  }
            binding.cartItem = item
        }
    }
}