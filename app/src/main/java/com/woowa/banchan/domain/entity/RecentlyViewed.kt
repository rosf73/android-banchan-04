package com.woowa.banchan.domain.entity

data class RecentlyViewed(
    val hash: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val nPrice: String? = null,
    val sPrice: String,
    val viewedAt: Long
)
