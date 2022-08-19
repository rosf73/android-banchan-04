package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.OrderDataSource
import com.woowa.banchan.data.local.entity.OrderEntity
import com.woowa.banchan.data.local.entity.OrderLineItemEntity
import com.woowa.banchan.data.local.entity.toOrder
import com.woowa.banchan.data.local.entity.toOrderLineItem
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

    override suspend fun addOrder(orderPair: Pair<Order, List<OrderLineItem>>) {
        val order = orderPair.first
        val orderLineItemList = orderPair.second.map {
            OrderLineItemEntity(
                orderId = order.id,
                name = it.name,
                imageUrl = it.imageUrl,
                quantity = it.quantity,
                price = it.price
            )
        }

        orderDataSource.addOrder(
            orderEntity = OrderEntity(order.id, order.orderedAt, order.status),
            orderLineItemEntity = orderLineItemList.toTypedArray()
        )
    }

    override suspend fun modifyOrder(order: Order) {
        orderDataSource.modifyOrder(OrderEntity(order.id, order.orderedAt, order.status))
    }
}