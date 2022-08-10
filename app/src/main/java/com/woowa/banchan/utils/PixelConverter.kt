package com.woowa.banchan.utils

import android.content.res.Resources

fun Float.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()