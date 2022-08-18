package com.woowa.banchan.ui.main.tabs.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemBanchanPlanBinding
import com.woowa.banchan.domain.entity.Category
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.ui.main.tabs.adapter.ProductAdapter

class PlanAdapter(
    private val planItems: List<Category>,
    private val onClick: (Product) -> Unit,
    private val onClickCart: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(
            ItemBanchanPlanBinding.inflate(inflater, parent, false),
            onClick,
            onClickCart = { onClickCart(it) }
        )
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

    class CategoryViewHolder(
        private val binding: ItemBanchanPlanBinding,
        private val onClick: (Product) -> Unit,
        private val onClickCart: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val productAdapter by lazy {
            ProductAdapter(
                onClick,
                onClickCart = { onClickCart(it) })
        }

        fun bind(item: Category) {
            binding.category = item.title
            binding.rvHome.adapter = productAdapter
            binding.rvHome.addOnItemTouchListener(HorizontalScrollListener())
            productAdapter.submitList(item.menus)
        }
    }

}