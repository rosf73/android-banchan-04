package com.woowa.banchan.domain.entity

data class OrderLineItem(
    val name: String,
    val imageUrl: String,
    val quantity: Int,
    val price: String
)