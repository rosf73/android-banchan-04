package com.woowa.banchan.ui.screen.main.tabs.plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemBanchanPlanBinding
import com.woowa.banchan.domain.entity.Category
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.ui.screen.main.tabs.adapter.ProductAdapter

class PlanAdapter(
    private val onClick: (Product) -> Unit,
    private val onClickCart: (Product) -> Unit
) : ListAdapter<Category, PlanAdapter.CategoryViewHolder>(categoryDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(
            ItemBanchanPlanBinding.inflate(inflater, parent, false),
            onClick,
            onClickCart = { onClickCart(it) }
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] == true)
                holder.bindCategoryList(getItem(position).menus)
        }
    }

    class CategoryViewHolder(
        private val binding: ItemBanchanPlanBinding,
        private val onClick: (Product) -> Unit,
        private val onClickCart: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val productAdapter by lazy {
            ProductAdapter(
                onClick,
                onClickCart = { onClickCart(it) }
            )
        }

        fun bind(item: Category) {
            binding.category = item.title
            binding.rvHome.adapter = productAdapter
            binding.rvHome.addOnItemTouchListener(NestedScrollListener())
            productAdapter.submitList(item.menus)
        }

        fun bindCategoryList(menus: List<Product>) {
            productAdapter.submitList(menus)
        }
    }
}

val categoryDiffUtil = object : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Category, newItem: Category): Any? {
        return if (oldItem.menus != newItem.menus) true else null
    }
}
