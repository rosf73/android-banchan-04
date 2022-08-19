package com.woowa.banchan.extensions

import java.util.*

fun Long.toTimeString(): String {
    val today = Calendar.getInstance()
    val target = Calendar.getInstance().apply { timeInMillis = this@toTimeString }

    val diffSec = (today.time.time - target.time.time) / 1000
    val diffMin = (today.time.time - target.time.time) / 60 / 1000
    val diffHour = (today.time.time - target.time.time) / 60 / 60 / 1000

    return if (diffSec < 60)
            "방금"
        else if (diffHour < 1)
           "${diffMin}분 전"
        else if (diffHour < 24)
           "${diffHour}시간 전"
        else if (diffHour < 24 * 30)
           "${diffHour / 24}일 전"
        else if (diffHour < 24 * 30 * 12)
           "${diffHour / (24 * 30)}달 전"
        else
           "${diffHour / (24 * 30 * 12)}년 전"
}