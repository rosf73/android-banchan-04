package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.entity.OrderDetailSection.Order
import com.woowa.banchan.domain.entity.OrderDetailSection.OrderLineItem
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    fun getAllOrder(): Flow<Result<Map<Order, List<OrderLineItem>>>>

    suspend fun addOrder(order: Order, vararg orderLineItem: OrderLineItem)

    suspend fun modifyOrder(order: Order)
}