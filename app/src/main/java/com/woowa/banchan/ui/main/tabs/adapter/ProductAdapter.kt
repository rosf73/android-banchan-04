package com.woowa.banchan.ui.main.tabs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemBanchanGridBinding
import com.woowa.banchan.databinding.ItemBanchanHorizontalBinding
import com.woowa.banchan.databinding.ItemBanchanVerticalBinding
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.domain.entity.ProductViewType

class ProductAdapter(
    private val onClick: (Product) -> Unit,
    private val onClickCart: (Product) -> Unit
) : ListAdapter<Product, RecyclerView.ViewHolder>(productDiffUtil) {

    private var viewType = ProductViewType.Horizontal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ProductViewType.Horizontal.ordinal -> {
                PlanViewHolder(
                    ItemBanchanHorizontalBinding.inflate(inflater, parent, false),
                    onClick,
                    onClickCart
                )
            }
            ProductViewType.Vertical.ordinal -> {
                LinearViewHolder(
                    ItemBanchanVerticalBinding.inflate(inflater, parent, false),
                    onClick,
                    onClickCart
                )
            }
            ProductViewType.Grid.ordinal -> {
                GridViewHolder(
                    ItemBanchanGridBinding.inflate(inflater, parent, false),
                    onClick,
                    onClickCart
                )
            }
            else -> {
                throw IllegalAccessException()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GridViewHolder -> holder.bind(getItem(position))
            is LinearViewHolder -> holder.bind(getItem(position))
            is PlanViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int = viewType.ordinal

    fun setViewType(viewType: ProductViewType) {
        this.viewType = viewType
    }

    class PlanViewHolder(
        private val binding: ItemBanchanHorizontalBinding,
        private val onClick: (Product) -> Unit,
        private val onClickCart: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            itemView.setOnClickListener { onClick(product) }
            binding.ivCart.setOnClickListener { onClickCart(product) }
            binding.product = product
            binding.executePendingBindings()
        }
    }

    class GridViewHolder(
        private val binding: ItemBanchanGridBinding,
        private val onClick: (Product) -> Unit,
        private val onClickCart: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            itemView.setOnClickListener { onClick(product) }
            binding.ivCart.setOnClickListener { onClickCart(product) }
            binding.product = product
            binding.executePendingBindings()
        }
    }

    class LinearViewHolder(
        private val binding: ItemBanchanVerticalBinding,
        private val onClick: (Product) -> Unit,
        private val onClickCart: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            itemView.setOnClickListener { onClick(product) }
            binding.ivCart.setOnClickListener { onClickCart(product) }
            binding.product = product
            binding.executePendingBindings()
        }
    }
}