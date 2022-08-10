package com.woowa.banchan.ui.tabs.common

import androidx.recyclerview.widget.DiffUtil
import com.woowa.domain.entity.Menu

val menuDiffUtil = object : DiffUtil.ItemCallback<Menu>() {
    override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
        return oldItem == newItem
    }
}