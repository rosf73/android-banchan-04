package com.woowa.banchan.ui.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.woowa.banchan.R

@BindingAdapter("src")
fun ImageView.setSource(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.iv_placeholder)
        .into(this)
}
