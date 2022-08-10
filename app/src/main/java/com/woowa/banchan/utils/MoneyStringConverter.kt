package com.woowa.banchan.utils

import java.text.DecimalFormat

fun Int.toMoneyString(): String = DecimalFormat("#,###").format(this) + "원"

fun Long.toMoneyString(): String = DecimalFormat("#,###").format(this) + "원"

fun String.toMoneyInt(): Int = this.replace(Regex(",|원"), "").toInt()