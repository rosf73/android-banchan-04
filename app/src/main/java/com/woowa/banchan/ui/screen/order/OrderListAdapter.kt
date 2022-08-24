package com.woowa.banchan.ui.screen.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemOrderBinding
import com.woowa.banchan.domain.entity.DeliveryStatus
import com.woowa.banchan.domain.entity.OrderInfo

class OrderListAdapter(
    private val onClickItem: (Long) -> Unit
) : PagingDataAdapter<OrderInfo, OrderListAdapter.ViewHolder>(orderListDiffUtil) {

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
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(
        private val binding: ItemOrderBinding,
        private val onClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OrderInfo) {
            binding.clItemView.setOnClickListener { onClick(item.id) }
            binding.count = item.count
            binding.deliveryState = item.status == DeliveryStatus.START
            binding.name = item.name
            binding.imageUrl = item.imageUrl
            binding.totalPrice = item.price
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
