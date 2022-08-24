package com.woowa.banchan.ui.extensions

import android.content.res.Resources

fun Float.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
