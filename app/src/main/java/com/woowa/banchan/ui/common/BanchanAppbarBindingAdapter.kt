package com.woowa.banchan.ui.common

import androidx.databinding.BindingAdapter

@BindingAdapter("onBackClick")
fun BanchanAppbar.onBackClick(method: () -> Unit) {
    this.onNavigationIconClick(method)
}