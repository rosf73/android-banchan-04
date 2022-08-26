package com.woowa.banchan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.woowa.banchan.domain.entity.OrderDetailSection.OrderLineItem
import com.woowa.banchan.extensions.toMoneyInt
import com.woowa.banchan.extensions.toMoneyString

@Entity(
    tableName = "order_line_item",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["order_id"]
        )
    ]
)
data class OrderLineItemEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "order_id") val orderId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "price") val price: Int
)

fun OrderLineItem.toOrderLineItemEntity(orderId: Long): OrderLineItemEntity {
    return OrderLineItemEntity(
        orderId = orderId,
        name = name,
        imageUrl = imageUrl,
        quantity = quantity,
        price = price.toMoneyInt()
    )
}

fun OrderLineItemEntity.toOrderLineItem(): OrderLineItem {
    return OrderLineItem(
        name, imageUrl, quantity, price.toMoneyString()
    )
}
