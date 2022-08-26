package com.woowa.banchan.domain.entity

sealed class OrderDetailSection {
    data class OrderLineItem(
        val name: String,
        val imageUrl: String,
        val quantity: Int,
        val price: String
    ) : OrderDetailSection()

    data class Order(
        val id: Long = 0,
        val orderedAt: Long,
        val status: DeliveryStatus,
        val count: Int = 0
    ) : OrderDetailSection()

    data class OrderFooter(
        val price: Int
    ) : OrderDetailSection()
}
