package com.woowa.banchan.domain.entity

class DetailProduct(
    val hash: String,
    val thumbs: List<String>,
    val sPrice: String,
    val nPrice: String?,
    val point: String,
    val deliveryInfo: String,
    val deliveryFee: String,
    val section: List<String>
) {
    val discountRate: String
        get() = if (nPrice == null) BLANK
        else {
            val tempS = sPrice.replace(Regex("${COMMA}${SEPARATOR}${WON}"), BLANK).toFloat()
            val tempN = nPrice.replace(Regex("${COMMA}${SEPARATOR}${WON}"), BLANK).toFloat()
            "${((tempN - tempS) / tempN * 100).toInt()}${PERCENT}"
        }

    companion object {
        const val BLANK = ""
        const val COMMA = ","
        const val SEPARATOR = "ㅣ"
        const val WON = "원"
        const val PERCENT = "%"
    }
}