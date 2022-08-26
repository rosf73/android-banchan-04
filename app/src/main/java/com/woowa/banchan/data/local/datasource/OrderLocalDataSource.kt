package com.woowa.banchan.data.local.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.entity.OrderEntity
import com.woowa.banchan.data.local.entity.OrderInfoView
import com.woowa.banchan.data.local.entity.OrderLineItemEntity
import com.woowa.banchan.data.local.entity.OrderLineItemView
import com.woowa.banchan.domain.entity.DeliveryStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderLocalDataSource @Inject constructor(
    private val orderDao: OrderDao
) : OrderDataSource {

    override fun getAllWithPage(): Flow<PagingData<OrderInfoView>> {
        return Pager(PagingConfig(pageSize = 10)) { orderDao.findAllWithPage() }.flow
    }

    override fun getStartOrderCount(): Flow<Int> {
        return orderDao.findOrderCountByStatus(DeliveryStatus.START.status)
    }

    override fun getOrderLineItem(orderId: Long): Flow<List<OrderLineItemView>> {
        return orderDao.findByOrderId(orderId)
    }

    override suspend fun addOrder(
        orderEntity: OrderEntity,
        vararg orderLineItemEntity: OrderLineItemEntity
    ): Long {
        val orderId = orderDao.insertOrder(orderEntity)
        val orderLineItemList = orderLineItemEntity.map { it.copy(orderId = orderId) }
        orderDao.insertOrderLineItem(*orderLineItemList.toTypedArray())

        return orderId
    }

    override suspend fun modifyOrder(orderEntity: OrderEntity): Int {
        return orderDao.updateOrder(orderEntity)
    }
}
