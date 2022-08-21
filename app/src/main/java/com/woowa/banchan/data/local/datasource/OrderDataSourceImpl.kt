package com.woowa.banchan.data.local.datasource

import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.entity.OrderEntity
import com.woowa.banchan.data.local.entity.OrderLineItemEntity
import com.woowa.banchan.data.local.entity.OrderLineItemView
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderDataSource {

    override fun getAllOrder(): Flow<List<OrderLineItemView>> {
        return orderDao.findAll()
    }

    override fun getOrderLineItem(orderId: Int): Flow<List<OrderLineItemView>> {
        return orderDao.findByOrderId(orderId)
    }

    override suspend fun addOrder(
        orderEntity: OrderEntity,
        vararg orderLineItemEntity: OrderLineItemEntity
    ) {
        orderDao.insertOrder(orderEntity)
        orderDao.insertOrderLineItem(*orderLineItemEntity)
    }

    override suspend fun modifyOrder(orderEntity: OrderEntity): Int {
        return orderDao.updateOrder(orderEntity)
    }
}