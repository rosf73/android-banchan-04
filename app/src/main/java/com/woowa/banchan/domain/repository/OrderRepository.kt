package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.entity.Order
import com.woowa.banchan.domain.entity.OrderLineItem
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    fun getAllOrder(): Flow<Result<Map<Order, List<OrderLineItem>>>>

    suspend fun addOrder(orderPair: Pair<Order, List<OrderLineItem>>)

    suspend fun modifyOrder(order: Order)
}