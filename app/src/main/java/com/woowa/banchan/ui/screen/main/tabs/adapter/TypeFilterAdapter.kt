package com.woowa.banchan.ui.screen.main.tabs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ItemTypeFilterBinding
import com.woowa.banchan.domain.entity.ProductViewType
import com.woowa.banchan.domain.entity.SortType

class TypeFilterAdapter(
    private val onClickItem: (SortType) -> Unit,
    private val onChangeType: (ProductViewType) -> Unit
) : RecyclerView.Adapter<TypeFilterAdapter.TypeFilterViewHolder>() {

    private lateinit var viewMode: ProductViewType
    private lateinit var sortType: SortType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeFilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTypeFilterBinding.inflate(inflater, parent, false)
        return TypeFilterViewHolder(binding, onClickItem, onChangeType)
    }

    override fun onBindViewHolder(holder: TypeFilterViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

    fun setViewMode(viewMode: ProductViewType) {
        this.viewMode = viewMode
    }

    fun setSortType(sortType: SortType) {
        this.sortType = sortType
    }

    inner class TypeFilterViewHolder(
        private val binding: ItemTypeFilterBinding,
        private val onClickItem: (SortType) -> Unit,
        private val onChangeType: (ProductViewType) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private val spinnerList = SortType.values().toList()
        val adapter = SpinnerAdapter(itemView.context, R.array.spinner)

        init {
            binding.spinnerFilter.adapter = adapter
        }

        fun bind() {
            binding.viewMode = viewMode
            binding.rgFilter.setOnCheckedChangeListener { _, id ->
                when (id) {
                    R.id.rb_grid -> onChangeType(ProductViewType.Grid)
                    R.id.rb_linear -> onChangeType(ProductViewType.Vertical)
                }
            }
            binding.spinnerFilter.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    private var isFirst = true

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (isFirst) {
                            isFirst = false
                            return
                        }

                        adapter.clickItem(position)
                        onClickItem(spinnerList[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) = Unit
                }
        }
    }
}
