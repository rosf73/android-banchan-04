package com.woowa.banchan.ui.main.tabs.adapter

import androidx.recyclerview.widget.DiffUtil
import com.woowa.banchan.domain.entity.Product

val productDiffUtil = object : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Product, newItem: Product): Any? {
        return if (oldItem.hasCart != newItem.hasCart) true else null
    }
}