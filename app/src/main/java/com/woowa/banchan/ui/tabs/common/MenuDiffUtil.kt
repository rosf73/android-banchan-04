package com.woowa.banchan.ui.tabs.common

import androidx.recyclerview.widget.DiffUtil
import com.woowa.banchan.domain.entity.Product

val menuDiffUtil = object : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}