package com.woowa.banchan.utils

fun String.substringShort(): String
    = if (this.length > 7) this.substring(0..6)+"..."
      else this