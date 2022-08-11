package com.woowa.banchan.ui.tabs.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemBanchanBinding
import com.woowa.banchan.domain.entity.Menu

class BanchanItemAdapter(
    private val onClick: () -> Unit
) : ListAdapter<Menu, BanchanItemAdapter.BanchanItemViewHolder>(menuDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanchanItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BanchanItemViewHolder(ItemBanchanBinding.inflate(inflater, parent, false), onClick)
    }

    override fun onBindViewHolder(holder: BanchanItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BanchanItemViewHolder(
        private val binding: ItemBanchanBinding,
        private val onClick: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: Menu) {
            itemView.setOnClickListener { onClick() }
            binding.menu = menu
        }
    }
}