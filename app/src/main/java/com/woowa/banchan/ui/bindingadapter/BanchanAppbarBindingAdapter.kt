package com.woowa.banchan.ui.bindingadapter

import androidx.databinding.BindingAdapter
import com.woowa.banchan.ui.customview.BanchanAppbar

@BindingAdapter("onNavigationClick")
fun BanchanAppbar.setOnNavigationIconClick(onClick: () -> Unit) {
    this.onNavigationIconClick(onClick)
}

@BindingAdapter("onFirstClick")
fun BanchanAppbar.setOnActionFirstClick(onClick: () -> Unit) {
    this.onActionFirstClick(onClick)
}

@BindingAdapter("onSecondClick")
fun BanchanAppbar.setOnActionSecondClick(onClick: () -> Unit) {
    this.onActionSecondClick(onClick)
}

@BindingAdapter("actionFirstOverflowText")
fun BanchanAppbar.setActionFirstOverflowCount(count: Int) {
    if (count == 0) this.removeOverflowText()
    else {
        val format = if (count >= 10) "10+" else count.toString()
        this.showOverflowText(format)
    }
}

@BindingAdapter("actionSecondActive")
fun BanchanAppbar.setActionSecondActive(active: Boolean) {
    this.setSecondActive(active)
}