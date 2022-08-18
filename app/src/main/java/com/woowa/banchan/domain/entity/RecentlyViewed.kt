package com.woowa.banchan.domain.entity

data class RecentlyViewed(
    val id: Long,
    val hash: String,
    val name: String,
    val imageUrl: String,
    val nPrice: String,
    val sPrice: String,
    val viewedAt: Long
)
