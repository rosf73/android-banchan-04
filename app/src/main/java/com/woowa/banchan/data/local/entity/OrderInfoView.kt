package com.woowa.banchan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import com.woowa.banchan.domain.entity.OrderInfo
import com.woowa.banchan.extensions.toMoneyString

@DatabaseView(
    """
SELECT a.id, a.status, count(*) as count, sum(b.price) as price,
(SELECT image_url FROM order_line_item WHERE order_id = a.id LIMIT 1) as image_url,
(SELECT name FROM order_line_item WHERE order_id = a.id LIMIT 1) as name 
FROM `order` as a JOIN order_line_item as b ON a.id = b.order_id 
GROUP BY a.id 
ORDER BY a.ordered_at DESC
    """
)
data class OrderInfoView(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "count") val count: Int,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image_url") val imageUrl: String
)

fun OrderInfoView.toOrderInfo(): OrderInfo {
    return OrderInfo(
        id, status, count, price.toMoneyString(), name, imageUrl
    )
}