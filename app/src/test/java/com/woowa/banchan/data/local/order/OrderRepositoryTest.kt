package com.woowa.banchan.data.local.order

import com.google.common.truth.Truth.assertThat
import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.datasource.OrderDataSource
import com.woowa.banchan.data.local.entity.OrderLineItemView
import com.woowa.banchan.data.local.entity.toOrderEntity
import com.woowa.banchan.data.local.entity.toOrderLineItemEntity
import com.woowa.banchan.data.local.repository.OrderRepositoryImpl
import com.woowa.banchan.domain.entity.DeliveryStatus
import com.woowa.banchan.domain.entity.OrderDetailSection
import com.woowa.banchan.domain.repository.OrderRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class OrderRepositoryTest {

    private lateinit var orderRepository: OrderRepository
    private lateinit var orderDataSource: OrderDataSource
    private lateinit var orderDao: OrderDao

    @Before
    fun setup() {
        orderDao = mockk(relaxed = true)
        orderDataSource = mockk(relaxed = true)
        orderRepository = OrderRepositoryImpl(orderDataSource)
    }

    @Test
    fun `배송_중인_주문의_개수를_가져_올_수_있다`() = runTest {
        // given
        val count = 2
        coEvery { orderDataSource.getStartOrderCount() } returns flow {
            emit(count)
        }

        // when
        val actualResult = orderRepository.getStartOrderCount().first().getOrThrow()

        // then
        assertThat(actualResult).isEqualTo(count)
    }

    @Test
    fun `주문한_상품_리스트들을_가져_올_수_있다`() = runTest {
        // given
        val result = listOf(
            OrderLineItemView(1L, 123L, "START", "음식1", "123", 1, 3000),
            OrderLineItemView(1L, 123L, "START", "음식2", "4567", 1, 3299),
            OrderLineItemView(1L, 123L, "START", "음식3", "890", 1, 1284)
        )
        coEvery { orderDataSource.getOrderLineItem(any()) } returns flow {
            emit(result)
        }

        val order = OrderDetailSection.Order(1L, 123L, DeliveryStatus.START, 3)
        val orderLineItem = listOf(
            OrderDetailSection.OrderLineItem("음식1", "123", 1, "3,000원"),
            OrderDetailSection.OrderLineItem("음식2", "4567", 1, "3,299원"),
            OrderDetailSection.OrderLineItem("음식3", "890", 1, "1,284원")
        )
        val expectResult = mapOf(order to orderLineItem)

        // when
        val actualResult = orderRepository.getOrderLineItem(1L).first().getOrThrow()

        // then
        assertThat(actualResult).isEqualTo(expectResult)
    }

    @Test
    fun `상품을_주문_할_수_있다`() = runTest {
        // given
        val order = OrderDetailSection.Order(1L, 123L, DeliveryStatus.START)
        val orderEntity = order.toOrderEntity()
        val orderLineItem = listOf(
            OrderDetailSection.OrderLineItem("음식1", "123", 1, "3,000원"),
            OrderDetailSection.OrderLineItem("음식2", "4567", 1, "3,299원")
        )
        val orderLineItemEntity = orderLineItem.map { it.toOrderLineItemEntity(order.id) }
        val id = 1L
        coEvery {
            orderDataSource.addOrder(
                orderEntity,
                *orderLineItemEntity.toTypedArray()
            )
        } returns id

        // when
        val actualResult = orderRepository.addOrder(order, *orderLineItem.toTypedArray())

        // then
        assertThat(actualResult).isEqualTo(id)
    }

    @Test
    fun `주문_상태를_변경_할_수_있다`() = runTest {
        // given
        val order = OrderDetailSection.Order(3L, 123L, DeliveryStatus.START)
        val orderEntity = order.toOrderEntity()
        val updateOrder = OrderDetailSection.Order(3L, 123L, DeliveryStatus.DONE, 2)
        val orderLineItem = listOf(
            OrderDetailSection.OrderLineItem("음식1", "123", 1, "3,000원"),
            OrderDetailSection.OrderLineItem("음식2", "4567", 1, "3,299원")
        )
        val orderLineItemEntity = orderLineItem.map { it.toOrderLineItemEntity(order.id) }
        val orderLineItemViewList = listOf(
            OrderLineItemView(3L, 123L, "DONE", "음식1", "123", 1, 3000),
            OrderLineItemView(3L, 123L, "DONE", "음식2", "4567", 1, 3299)
        )
        val result = 1

        coEvery {
            orderDataSource.addOrder(
                orderEntity,
                *orderLineItemEntity.toTypedArray()
            )
        } returns order.id
        coEvery { orderDataSource.modifyOrder(any()) } returns result
        coEvery { orderDataSource.getOrderLineItem(any()) } returns flow {
            emit(orderLineItemViewList)
        }

        // when
        val expectId = 3L
        val expectResult = mapOf(updateOrder to orderLineItem)
        val actualId = orderRepository.addOrder(order, *orderLineItem.toTypedArray())
        val actualResult = orderRepository.modifyOrder(order).getOrThrow()
        val actualResultView = orderRepository.getOrderLineItem(order.id).first().getOrThrow()

        // then
        assertThat(actualId).isEqualTo(expectId)
        assertThat(actualResult).isEqualTo(result)
        assertThat(actualResultView).isEqualTo(expectResult)
    }
}
