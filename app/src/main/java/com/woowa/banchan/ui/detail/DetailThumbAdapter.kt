package com.woowa.banchan.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.woowa.banchan.R

class DetailThumbAdapter(
    private val context: Context,
    private val items: List<String>
) : RecyclerView.Adapter<DetailThumbAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_detail_thumb, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(items[position]).into(holder.imageUrl)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageUrl: ImageView = view.findViewById(R.id.iv_thumbnail)
    }
}