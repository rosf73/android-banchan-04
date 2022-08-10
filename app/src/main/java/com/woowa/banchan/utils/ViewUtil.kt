package com.woowa.banchan.utils

import android.view.View

fun Boolean.toVisibility(): Int = if (this) View.VISIBLE else View.GONE