package com.woowa.banchan.utils

import android.content.Context
import kotlin.math.round

fun Float.toPx(context: Context): Int = round(this * context.resources.displayMetrics.density).toInt()