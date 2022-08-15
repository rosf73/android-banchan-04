package com.woowa.banchan.ui.tabs.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ItemTypeFilterBinding
import com.woowa.banchan.domain.entity.SortType

class TypeFilterAdapter(
    private val onClickItem: (SortType) -> Unit,
    private val onChangeType: (String) -> Unit
) : RecyclerView.Adapter<TypeFilterAdapter.TypeFilterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeFilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTypeFilterBinding.inflate(inflater, parent, false)
        return TypeFilterViewHolder(binding, onClickItem, onChangeType)
    }

    override fun onBindViewHolder(holder: TypeFilterViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

    class TypeFilterViewHolder(
        private val binding: ItemTypeFilterBinding,
        private val onClickItem: (SortType) -> Unit,
        private val onChangeType: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val spinnerList = SortType.values().toList()

        fun bind() {
            val adapter = SpinnerAdapter(itemView.context, R.array.spinner)
            binding.spinnerFilter.adapter = adapter
            binding.rgFilter.setOnCheckedChangeListener { _, id ->
                when (id) {
                    R.id.rb_grid -> onChangeType("grid")
                    R.id.rb_linear -> onChangeType("linear")
                }
            }
            binding.spinnerFilter.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        adapter.clickItem(position)
                        onClickItem(spinnerList[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) = Unit

                }
        }

    }
}