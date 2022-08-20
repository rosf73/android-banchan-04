package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.OrderDataSource
import com.woowa.banchan.data.local.entity.*
import com.woowa.banchan.domain.entity.Order
import com.woowa.banchan.domain.entity.OrderLineItem
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
): OrderRepository {

    private val orderMap = mutableMapOf<Order, MutableList<OrderLineItem>>()

    override fun getAllOrder(): Flow<Result<Map<Order, List<OrderLineItem>>>> = flow {
        orderDataSource.getAllOrder()
            .collect { list ->
                list.forEach {
                    val keyOrder = it.toOrder()
                    val orderLineItem = it.toOrderLineItem()

                    if (orderMap.containsKey(keyOrder))
                        orderMap[keyOrder]?.add(orderLineItem)
                    else
                        orderMap[keyOrder] = mutableListOf(orderLineItem)
                }
                emit(Result.success(orderMap))
            }
    }.catch {
        emit(Result.failure(NotFoundProductsException()))
    }

    override suspend fun addOrder(order: Order, vararg orderLineItem: OrderLineItem) {
        val orderLineItemList = orderLineItem.map {
            it.toOrderLineItemEntity(order.id)
        }.toTypedArray()

        orderDataSource.addOrder(order.toOrderEntity(), *orderLineItemList)
    }

    override suspend fun modifyOrder(order: Order) {
        orderDataSource.modifyOrder(OrderEntity(order.id, order.orderedAt, order.status))
    }
}