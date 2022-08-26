package com.woowa.banchan.domain.entity

data class Cart(
    val id: Long = DEFAULT_ID,
    val hash: String,
    val name: String,
    val imageUrl: String,
    var quantity: Int,
    val price: String,
    var checked: Boolean = false
) {
    companion object {
        const val DEFAULT_ID = 0L
    }
}
