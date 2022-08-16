package com.woowa.banchan.ui.recently

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemRecentlyBinding

class RecentlyAdapter(
    private val recentlyItems: List<TestRecently>,
    private val onClick: (TestRecently) -> Unit,
    private val onClickCart: (TestRecently) -> Unit
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
        private val onClick: (TestRecently) -> Unit,
        private val onClickCart: (TestRecently) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TestRecently) {
            binding.recentlyItem = item
        }
    }
}