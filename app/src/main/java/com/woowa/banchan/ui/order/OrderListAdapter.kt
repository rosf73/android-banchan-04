package com.woowa.banchan.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemOrderBinding
import com.woowa.banchan.domain.entity.OrderInfo
import com.woowa.banchan.extensions.toMoneyInt
import com.woowa.banchan.extensions.toMoneyString

class OrderListAdapter(
    private val onClickItem: () -> Unit
) : ListAdapter<OrderInfo, OrderListAdapter.ViewHolder>(orderListDiffUtil) {

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

        fun bind(item: OrderInfo) {
            binding.clItemView.setOnClickListener { onClick() }
            binding.count = item.count
            binding.deliveryState = item.status == "START"
            binding.name = item.name
            binding.imageUrl = item.imageUrl
            binding.totalPrice = item.price.toMoneyInt().toMoneyString()
        }
    }
}

val orderListDiffUtil = object : DiffUtil.ItemCallback<OrderInfo>() {
    override fun areItemsTheSame(
        oldItem: OrderInfo,
        newItem: OrderInfo
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: OrderInfo,
        newItem: OrderInfo
    ): Boolean {
        return oldItem == newItem
    }

}