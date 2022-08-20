package com.woowa.banchan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.woowa.banchan.domain.entity.Order

@Entity(tableName = "order")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "ordered_at") val orderedAt: Long,
    @ColumnInfo(name = "status") val status: String = "START" // "START" OR "DONE"
)

fun Order.toOrderEntity(): OrderEntity {
    return OrderEntity(id, orderedAt, status)
}