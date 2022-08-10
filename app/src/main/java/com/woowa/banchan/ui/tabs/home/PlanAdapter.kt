package com.woowa.banchan.ui.tabs.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.LayoutBanchanHorizontalBinding
import com.woowa.banchan.ui.tabs.common.BanchanItemAdapter
import com.woowa.domain.entity.Category

class PlanAdapter(
    private val planItems: List<Category>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(LayoutBanchanHorizontalBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder -> {
                val item = planItems[position] as Category
                holder.bind(planItems[position])
            }
        }
    }

    override fun getItemCount(): Int = planItems.size

    class CategoryViewHolder(private val binding: LayoutBanchanHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val banchanItemAdapter by lazy { BanchanItemAdapter() }

        fun bind(item: Category) {
            binding.category = item.title
            binding.rvHome.adapter = banchanItemAdapter
            banchanItemAdapter.submitList(item.menus)
        }
    }

}