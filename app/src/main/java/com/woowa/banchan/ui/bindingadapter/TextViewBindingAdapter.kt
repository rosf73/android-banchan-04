package com.woowa.banchan.ui.bindingadapter

import android.graphics.Paint
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.woowa.banchan.BanchanApplication
import com.woowa.banchan.R
import com.woowa.banchan.extensions.toMoneyInt
import com.woowa.banchan.extensions.toMoneyString
import com.woowa.banchan.ui.extensions.toVisibility

@BindingAdapter("price", "quantity")
fun TextView.setTotalPrice(price: String?, quantity: Int) {
    text = if (price.isNullOrEmpty()) "0원"
    else (price.toMoneyInt() * quantity).toMoneyString()
}

@BindingAdapter("textStrikeThrough")
fun TextView.setTextStrikeThrough(condition: Boolean) {
    if (condition) {
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}

@BindingAdapter("visibility")
fun TextView.setVisibility(condition: Boolean) {
    visibility = condition.toVisibility()
}

@BindingAdapter("count", "name")
fun TextView.setOrderTitle(count: Int, name: String) {
    text = if (count == 1) name
    else "$name 외 ${count - 1}개"
}

@BindingAdapter("state")
fun TextView.setOrderState(state: Boolean) {
    if (state) {
        text = BanchanApplication.instance.getString(R.string.order_start)
        setTextColor(ContextCompat.getColor(BanchanApplication.instance, R.color.primary_accent))
    } else {
        text = BanchanApplication.instance.getString(R.string.order_done)
        setTextColor(ContextCompat.getColor(BanchanApplication.instance, R.color.black))
    }
}
