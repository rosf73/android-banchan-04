package com.woowa.banchan.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.R
import com.woowa.banchan.utils.urlToBitmap
import kotlinx.coroutines.CoroutineScope

class DetailThumbAdapter(
    private val scope: CoroutineScope,
    private val items: List<String>
): RecyclerView.Adapter<DetailThumbAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_detail_thumb, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        urlToBitmap(items[position], scope) { bitmap ->
            holder.imageUrl.setImageBitmap(bitmap)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageUrl: ImageView = view.findViewById(R.id.iv_thumbnail)
    }
}