package com.woowa.banchan.ui.common

import androidx.databinding.BindingAdapter

@BindingAdapter("onNavigationClick")
fun BanchanAppbar.setOnNavigationIconClick(onClick: () -> Unit) {
    this.onNavigationIconClick(onClick)
}