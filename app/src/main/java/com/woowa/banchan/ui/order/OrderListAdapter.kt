package com.woowa.banchan.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemOrderBinding
import com.woowa.banchan.domain.entity.OrderDetailSection.*
import com.woowa.banchan.extensions.toMoneyString

class OrderListAdapter(
    private val onClickItem: () -> Unit
) : ListAdapter<Triple<Order, OrderLineItem, OrderFooter>, OrderListAdapter.ViewHolder>(orderListDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemOrderBinding.inflate(
                layoutInflater,
                parent,
                false
            ),
            onClick = onClickItem
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemOrderBinding,
        private val onClick: () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Triple<Order, OrderLineItem, OrderFooter>) {
            binding.clItemView.setOnClickListener { onClick() }
            binding.count = item.first.count
            binding.deliveryState = item.first.status == "START"
            binding.orderLineItem = item.second
            binding.totalPrice = item.third.price.toMoneyString()
        }
    }
}

val orderListDiffUtil = object : DiffUtil.ItemCallback<Triple<Order, OrderLineItem, OrderFooter>>() {
    override fun areItemsTheSame(
        oldItem: Triple<Order, OrderLineItem, OrderFooter>,
        newItem: Triple<Order, OrderLineItem, OrderFooter>
    ): Boolean {
        return oldItem.first.id == newItem.first.id
    }

    override fun areContentsTheSame(
        oldItem: Triple<Order, OrderLineItem, OrderFooter>,
        newItem: Triple<Order, OrderLineItem, OrderFooter>
    ): Boolean {
        return oldItem == newItem
    }

}