package com.woowa.banchan.utils

import com.woowa.banchan.BanchanApplication
import kotlin.math.round

fun Float.toPx(): Int
    = round(this * BanchanApplication.instance.resources.displayMetrics.density).toInt()