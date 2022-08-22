package com.woowa.banchan.data.local.datasource

import com.woowa.banchan.data.local.entity.OrderEntity
import com.woowa.banchan.data.local.entity.OrderInfoView
import com.woowa.banchan.data.local.entity.OrderLineItemEntity
import com.woowa.banchan.data.local.entity.OrderLineItemView
import kotlinx.coroutines.flow.Flow

interface OrderDataSource {

    fun getAllOrderInfo(): Flow<List<OrderInfoView>>

    fun getOrderLineItem(orderId: Long): Flow<List<OrderLineItemView>>

    suspend fun addOrder(orderEntity: OrderEntity, vararg orderLineItemEntity: OrderLineItemEntity): Long

    suspend fun modifyOrder(orderEntity: OrderEntity): Int
}