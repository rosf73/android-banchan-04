package com.woowa.banchan.domain.entity

data class OrderInfo(
    val id: Long = 0,
    val status: String,
    val count: Int = 0,
    val price: String,
    val name: String,
    val imageUrl: String
)