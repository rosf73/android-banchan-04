package com.woowa.banchan.ui.common

import androidx.databinding.BindingAdapter

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