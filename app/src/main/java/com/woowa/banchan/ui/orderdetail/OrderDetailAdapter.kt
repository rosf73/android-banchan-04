package com.woowa.banchan.ui.orderdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemOrderDetailBinding
import com.woowa.banchan.databinding.ItemOrderDetailFooterBinding
import com.woowa.banchan.databinding.ItemOrderDetailHeaderBinding
import com.woowa.banchan.domain.entity.OrderDetailSection
import com.woowa.banchan.domain.entity.OrderDetailSection.*
import com.woowa.banchan.extensions.getDiffTime
import com.woowa.banchan.extensions.toMoneyInt
import com.woowa.banchan.extensions.toMoneyString
import com.woowa.banchan.ui.cart.components.CartPriceColumn

const val ORDER_HEADER = 0
const val ORDER_CONTENT = 1
const val ORDER_FOOTER = 2

class OrderDetailAdapter(
    private val onComplete: () -> Unit
) : ListAdapter<OrderDetailSection, RecyclerView.ViewHolder>(orderDetailDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ORDER_HEADER -> OrderDetailHeaderViewHolder(
                ItemOrderDetailHeaderBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                ),
                onComplete
            )
            ORDER_CONTENT -> OrderDetailViewHolder(
                ItemOrderDetailBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            ORDER_FOOTER -> OrderDetailFooterViewHolder(
                ItemOrderDetailFooterBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            else -> throw Exception("unknown type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OrderDetailHeaderViewHolder -> holder.bind(getItem(position) as Order)
            is OrderDetailViewHolder -> holder.bind(getItem(position) as OrderLineItem)
            is OrderDetailFooterViewHolder -> holder.bind(getItem(position) as OrderFooter)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is Order -> ORDER_HEADER
            is OrderLineItem -> ORDER_CONTENT
            is OrderFooter -> ORDER_FOOTER
        }
    }

    fun submitHeaderAndOrderList(orderList: Map<Order, List<OrderDetailSection>>) {
        val orderItem = mutableListOf<OrderDetailSection>()
        orderList.entries.forEach { entry ->
            orderItem.add(entry.key)
            orderItem.addAll(entry.value)
            val value = entry.value
            val totalSum = value.fold(0) { sum, orderLineItem ->
                sum + if (orderLineItem is OrderLineItem) orderLineItem.quantity * orderLineItem.price.toMoneyInt() else 0
            }
            orderItem.add(OrderFooter(totalSum))
        }
        submitList(orderItem)
    }


    inner class OrderDetailHeaderViewHolder(
        private val binding: ItemOrderDetailHeaderBinding,
        private val onComplete: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.count = order.count
            binding.deliveryState = order.status == "Start"

            if (order.status == "Start") {
                binding.tvDeliveryTimeTitle.isVisible = true
                val minutes = (order.orderedAt + 1000 * 60 * 2).getDiffTime()
                if (minutes > 0) binding.tvDeliveryTime.text = "${minutes}분"
                else onComplete()
            } else {
                binding.tvDeliveryTimeTitle.isGone = true
                binding.tvDeliveryTime.isGone = true
            }
        }
    }

    class OrderDetailViewHolder(private val binding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderLineItem: OrderLineItem) {
            binding.orderLineItem = orderLineItem
            binding.totalPrice = orderLineItem.price
        }
    }

    class OrderDetailFooterViewHolder(
        private val binding: ItemOrderDetailFooterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(orderFooter: OrderFooter) {
            binding.composePrice.setContent {
                CartPriceColumn(
                    totalPrice = orderFooter.price,
                    deliveryFee = 2500
                )
            }
        }
    }
}


val orderDetailDiffUtil = object : DiffUtil.ItemCallback<OrderDetailSection>() {
    override fun areItemsTheSame(
        oldItem: OrderDetailSection,
        newItem: OrderDetailSection
    ): Boolean {
        if (oldItem is Order && newItem is Order) {
            return oldItem.id == newItem.id
        } else if (oldItem is OrderLineItem && newItem is OrderLineItem) {
            return oldItem.name == newItem.name
        } else if (oldItem is OrderFooter && newItem is OrderFooter) {
            return oldItem.price == newItem.price
        }
        return false
    }

    override fun areContentsTheSame(
        oldItem: OrderDetailSection,
        newItem: OrderDetailSection
    ): Boolean {
        return oldItem == newItem
    }
}