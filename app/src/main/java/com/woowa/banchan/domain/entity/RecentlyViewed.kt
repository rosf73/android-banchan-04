package com.woowa.banchan.domain.entity

import com.woowa.banchan.extensions.toTimeString

data class RecentlyViewed(
    val hash: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val nPrice: String? = null,
    val sPrice: String,
    val viewedAt: Long
)

fun RecentlyViewed.toProduct(): Product {
    return Product(
        detailHash = hash,
        title = name,
        description = description,
        image = imageUrl,
        nPrice = nPrice,
        sPrice = sPrice,
        viewedAt = viewedAt.toTimeString(),
        alt = "",
        badge = emptyList(),
        deliveryType = emptyList()
    )
}