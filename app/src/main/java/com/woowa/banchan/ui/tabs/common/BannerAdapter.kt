package com.woowa.banchan.ui.tabs.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.LayoutBannerBinding

class BannerAdapter(
    private val banners: List<String>,
    private val hasLabel: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BannerViewHolder(LayoutBannerBinding.inflate(inflater, parent, false), hasLabel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BannerViewHolder -> {
                holder.bind(banners[position])
            }
        }
    }

    override fun getItemCount(): Int = banners.size

    class BannerViewHolder(
        private val binding: LayoutBannerBinding,
        private val hasLabel: Boolean
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.bannerTitle = item
            binding.isShowLabel = hasLabel
        }
    }
}