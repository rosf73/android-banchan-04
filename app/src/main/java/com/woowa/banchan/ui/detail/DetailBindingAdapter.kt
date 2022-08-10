package com.woowa.banchan.ui.detail

import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.woowa.banchan.utils.toMoneyInt
import com.woowa.banchan.utils.toMoneyString
import com.woowa.banchan.utils.toVisibility

@BindingAdapter("visibility")
fun TextView.setVisibility(condition: Boolean) {
    visibility = condition.toVisibility()
}

@BindingAdapter("price", "quantity")
fun TextView.setTotalPrice(price: String, quantity: Int) {
    text = (price.toMoneyInt() * quantity).toMoneyString()
}

@BindingAdapter("textStrikeThrough")
fun TextView.setTextStrikeThrough(condition: Boolean) {
    if (condition) {
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}

@BindingAdapter("src")
fun ImageView.setSource(url: String) {
    Glide.with(context).load(url).into(this)
}