package com.woowa.banchan.data.local

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.database.AppDatabase
import com.woowa.banchan.data.local.entity.OrderEntity
import com.woowa.banchan.data.local.entity.OrderLineItemEntity
import com.woowa.banchan.data.local.entity.OrderLineItemView
import com.woowa.banchan.domain.entity.DeliveryStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class OrderDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: OrderDao

    private lateinit var orderLineItemList: Array<OrderLineItemEntity>

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.orderDao()
        orderLineItemList = arrayOf(
            OrderLineItemEntity(
                1L,
                1L,
                "새콤달콤 오징어무침",
                "http://public.codesquad.kr/jk/storeapp/data/side/48_ZIP_P_5008_T.jpg",
                3,
                18000
            ),
            OrderLineItemEntity(
                2L,
                1L,
                "새콤달콤 문어무침",
                "http://public.codesquad.kr/jk/storeapp/data/side/48_ZIP_P_5008_T.jpg",
                1,
                6000
            ),
            OrderLineItemEntity(
                3L,
                1L,
                "엄청매운 오이무침",
                "http://public.codesquad.kr/jk/storeapp/data/side/48_ZIP_P_5008_T.jpg",
                2,
                9000
            )
        )
    }

    @Test
    fun `주문을_할_수_있다`() = runTest {
        val order = OrderEntity(1L, System.currentTimeMillis(), DeliveryStatus.DONE.status)
        dao.insertOrder(order)
        dao.insertOrderLineItem(*orderLineItemList)
        val expected = mutableListOf<OrderLineItemView>()
        (0..2).forEach { idx ->
            expected.add(
                OrderLineItemView(
                    order.id, order.orderedAt, order.status,
                    orderLineItemList[idx].name, orderLineItemList[idx].imageUrl,
                    orderLineItemList[idx].quantity, orderLineItemList[idx].price
                )
            )
        }

        val orderInformation = dao.findByOrderId(order.id).first()
        assertThat(orderInformation).isEqualTo(expected)
    }

    @Test
    fun `주문과_관계_없는_주문항목_넣는_경우_에러발생`() = runTest {
        try {
            dao.insertOrderLineItem(*orderLineItemList)
        } catch (e: SQLiteConstraintException) {
            assertThat(e.message).contains("FOREIGN KEY constraint failed")
        }
    }

    @After
    fun teardown() {
        database.close()
    }
}
