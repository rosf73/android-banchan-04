package com.woowa.banchan.data.local.dao

import androidx.room.*
import com.woowa.banchan.data.local.entity.OrderEntity
import com.woowa.banchan.data.local.entity.OrderInfoView
import com.woowa.banchan.data.local.entity.OrderLineItemEntity
import com.woowa.banchan.data.local.entity.OrderLineItemView
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Query("SELECT * FROM OrderInfoView")
    fun findAllGroupByOrderId(): Flow<List<OrderInfoView>>

    @Query("SELECT * FROM order_line_item, `order` WHERE order_id = :orderId AND `order`.id = order_line_item.order_id")
    fun findByOrderId(orderId: Int): Flow<List<OrderLineItemView>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrder(orderEntity: OrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrderLineItem(vararg orderLineItemEntity: OrderLineItemEntity)

    @Update
    suspend fun updateOrder(orderEntity: OrderEntity): Int
}