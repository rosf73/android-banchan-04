package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.entity.OrderDetailSection.Order
import com.woowa.banchan.domain.entity.OrderDetailSection.OrderLineItem
import com.woowa.banchan.domain.entity.OrderInfo
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    fun getAllOrder(): Flow<Result<List<OrderInfo>>>

    fun getOrderLineItem(orderId: Int): Flow<Result<Map<Order, List<OrderLineItem>>>>

    suspend fun addOrder(order: Order, vararg orderLineItem: OrderLineItem): Long

    suspend fun modifyOrder(order: Order) : Result<Int>
}