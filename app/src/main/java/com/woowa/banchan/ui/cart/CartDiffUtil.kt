package com.woowa.banchan.ui.cart

import androidx.recyclerview.widget.DiffUtil

val cartDiffUtil = object : DiffUtil.ItemCallback<TestCartItem>() {
    override fun areItemsTheSame(oldItem: TestCartItem, newItem: TestCartItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: TestCartItem, newItem: TestCartItem): Boolean {
        return oldItem == newItem
    }
}