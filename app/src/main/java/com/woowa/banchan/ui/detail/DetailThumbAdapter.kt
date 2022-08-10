package com.woowa.banchan.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemDetailSectionBinding
import com.woowa.banchan.databinding.ItemDetailThumbBinding

class DetailThumbAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<DetailThumbAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemDetailThumbBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(
        private val binding: ItemDetailThumbBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            binding.url = url
        }
    }
}