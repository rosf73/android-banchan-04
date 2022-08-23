package com.woowa.banchan.ui.screen.main.tabs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ItemCountFilterBinding
import com.woowa.banchan.domain.entity.SortType

class CountFilterAdapter(
    private val onClickItem: (SortType) -> Unit
) : RecyclerView.Adapter<CountFilterAdapter.CounterFilterViewHolder>() {

    private var count = 0
    private lateinit var sortType: SortType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterFilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCountFilterBinding.inflate(inflater, parent, false)
        return CounterFilterViewHolder(binding, onClickItem)
    }

    override fun onBindViewHolder(holder: CounterFilterViewHolder, position: Int) {
        holder.bind(count)
    }

    override fun getItemCount(): Int = 1

    fun submitTotalCount(count: Int) {
        this.count = count
        notifyDataSetChanged()
    }

    fun setSortType(sortType: SortType) {
        this.sortType = sortType
    }

    inner class CounterFilterViewHolder(
        private val binding: ItemCountFilterBinding,
        private val onClickItem: (SortType) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val spinnerList = SortType.values().toList()
        val adapter = SpinnerAdapter(itemView.context, R.array.spinner)

        init {
            binding.spinnerFilter.adapter = adapter
        }

        fun bind(count: Int) {
            binding.count = count
            binding.spinnerFilter.setSelection(sortType.ordinal)
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