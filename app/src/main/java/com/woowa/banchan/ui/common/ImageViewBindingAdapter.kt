package com.woowa.banchan.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("src")
fun ImageView.setSource(url: String) {
    Glide.with(context).load(url).into(this)
}