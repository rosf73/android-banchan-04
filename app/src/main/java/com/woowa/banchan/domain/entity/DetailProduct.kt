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
        get() = if (nPrice == null) ""
        else {
            val tempS = sPrice.replace(Regex(",|원"), "").toFloat()
            val tempN = nPrice.replace(Regex(",|원"), "").toFloat()
            "${((tempN - tempS) / tempN * 100).toInt()}%"
        }

    companion object {
        const val BLANK = ""
        val default = DetailProduct("", emptyList(), "0", null, "", "", "", emptyList())
    }
}
