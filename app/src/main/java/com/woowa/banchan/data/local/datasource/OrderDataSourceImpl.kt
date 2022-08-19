package com.woowa.banchan.data.local.datasource

import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.entity.OrderEntity
import com.woowa.banchan.data.local.entity.OrderLineItemEntity
import com.woowa.banchan.data.local.entity.OrderLineItemView
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderDao: OrderDao
): OrderDataSource {

    override fun getAllOrder(): Flow<List<OrderLineItemView>> {
        return orderDao.findAll()
    }

    override suspend fun addOrder(orderEntity: OrderEntity, vararg orderLineItemEntity: OrderLineItemEntity) {
        orderDao.insertOrder(orderEntity)
        orderLineItemEntity.forEach {
            orderDao.insertOrderLineItem(it)
        }
    }

    override suspend fun modifyOrder(orderEntity: OrderEntity) {
        orderDao.updateOrder(orderEntity)
    }
}