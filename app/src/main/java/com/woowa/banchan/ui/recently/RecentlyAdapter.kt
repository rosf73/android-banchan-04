package com.woowa.banchan.ui.recently

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemRecentlyBinding
import com.woowa.banchan.domain.entity.Product

class RecentlyAdapter(
    private val recentlyItems: List<Product>,
    private val onClick: (Product) -> Unit,
    private val onClickCart: (Product) -> Unit
) : RecyclerView.Adapter<RecentlyAdapter.RecentlyItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecentlyItemViewHolder(
            ItemRecentlyBinding.inflate(inflater, parent, false),
            onClick = onClick,
            onClickCart = onClickCart
        )
    }

    override fun onBindViewHolder(holder: RecentlyItemViewHolder, position: Int) {
        holder.bind(recentlyItems[position])
    }

    override fun getItemCount(): Int = recentlyItems.size

    class RecentlyItemViewHolder(
        private val binding: ItemRecentlyBinding,
        private val onClick: (Product) -> Unit,
        private val onClickCart: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            itemView.setOnClickListener { onClick(item) }
            binding.ivCart.setOnClickListener { onClickCart(item) }
            binding.recentlyItem = item
            binding.executePendingBindings()
        }
    }
}