package com.woowa.banchan.ui.tabs.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemBanchanBinding
import com.woowa.banchan.databinding.ItemBanchanVerticalBinding
import com.woowa.banchan.domain.entity.Product

class BanchanItemAdapter(
    private val onClick: (Product) -> Unit,
    private val onClickCart: (Product) -> Unit
) : ListAdapter<Product, RecyclerView.ViewHolder>(menuDiffUtil) {

    private var viewType = ProductViewType.Grid

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ProductViewType.Grid.ordinal -> {
                BanchanItemViewHolder(
                    ItemBanchanBinding.inflate(inflater, parent, false),
                    onClick,
                    onClickCart
                )
            }
            ProductViewType.Vertical.ordinal -> {
                BanchanLinearItemViewHolder(
                    ItemBanchanVerticalBinding.inflate(inflater, parent, false),
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
            is BanchanItemViewHolder -> holder.bind(getItem(position))
            is BanchanLinearItemViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int = viewType.ordinal

    fun setViewType(viewType: ProductViewType) {
        this.viewType = viewType
    }

    class BanchanItemViewHolder(
        private val binding: ItemBanchanBinding,
        private val onClick: (Product) -> Unit,
        private val onClickCart: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            itemView.setOnClickListener { onClick(product) }
            binding.ivCart.setOnClickListener { onClickCart(product) }
            binding.product = product
        }
    }

    class BanchanLinearItemViewHolder(
        private val binding: ItemBanchanVerticalBinding,
        private val onClick: (Product) -> Unit,
        private val onClickCart: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            itemView.setOnClickListener { onClick(product) }
            binding.ivCart.setOnClickListener { onClickCart(product) }
            binding.product = product
        }
    }

    enum class ProductViewType {
        Grid, Vertical
    }
}