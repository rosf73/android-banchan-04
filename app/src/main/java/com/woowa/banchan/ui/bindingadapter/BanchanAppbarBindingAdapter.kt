package com.woowa.banchan.ui.common

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