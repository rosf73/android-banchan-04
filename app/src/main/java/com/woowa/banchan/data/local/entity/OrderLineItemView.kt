package com.woowa.banchan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import com.woowa.banchan.domain.entity.Order
import com.woowa.banchan.domain.entity.OrderLineItem

@DatabaseView(
    """
    SELECT a.id, a.ordered_at, a.status, b.name, b.image_url, b.quantity, b.price FROM `order` as a JOIN order_line_item as b ON a.id = b.order_id 
"""
)
data class OrderLineItemView(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "ordered_at") val orderedAt: Long,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "price") val price: String
)

fun OrderLineItemView.toOrder(): Order {
    return Order(
        id, orderedAt, status
    )
}

fun OrderLineItemView.toOrderLineItem(): OrderLineItem {
    return OrderLineItem(
        name, imageUrl, quantity, price
    )
}