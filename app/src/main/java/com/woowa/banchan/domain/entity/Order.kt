package com.woowa.banchan.domain.entity

data class Order(
    val id: Long = 0,
    val orderedAt: Long,
    val status: String
)