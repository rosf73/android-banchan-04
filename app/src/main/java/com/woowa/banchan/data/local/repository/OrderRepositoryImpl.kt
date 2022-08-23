package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.OrderDataSource
import com.woowa.banchan.data.local.entity.*
import com.woowa.banchan.domain.entity.OrderDetailSection.Order
import com.woowa.banchan.domain.entity.OrderDetailSection.OrderLineItem
import com.woowa.banchan.domain.entity.OrderInfo
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
) : OrderRepository {

    override fun getAllOrder(): Flow<Result<List<OrderInfo>>> = flow {
        orderDataSource.getAllOrderInfo()
            .collect { list ->
                emit(Result.success(list.map { it.toOrderInfo() }))
            }
    }.catch {
        emit(Result.failure(NotFoundProductsException()))
    }.flowOn(Dispatchers.IO)

    override fun getOrderLineItem(orderId: Long): Flow<Result<Map<Order, List<OrderLineItem>>>> =
        flow {
            orderDataSource.getOrderLineItem(orderId)
                .collect { list ->
                    val orderMap = mutableMapOf<Order, MutableList<OrderLineItem>>()
                    list.forEach {
                        val keyOrder = it.toOrder().copy(count = list.size)
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

    override suspend fun addOrder(order: Order, vararg orderLineItem: OrderLineItem): Long {
        val orderLineItemList = orderLineItem.map {
            it.toOrderLineItemEntity(order.id)
        }.toTypedArray()

        return orderDataSource.addOrder(order.toOrderEntity(), *orderLineItemList)
    }

    override suspend fun modifyOrder(order: Order): Result<Int> {
        return runCatching {
            orderDataSource.modifyOrder(
                OrderEntity(
                    order.id,
                    order.orderedAt,
                    order.status.status
                )
            )
        }
    }
}