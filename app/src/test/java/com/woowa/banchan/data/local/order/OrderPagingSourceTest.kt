package com.woowa.banchan.data.local.order

import androidx.paging.PagingSource
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.datasource.OrderPagingSource
import com.woowa.banchan.data.local.entity.OrderInfoView
import com.woowa.banchan.data.local.entity.toOrderInfo
import com.woowa.banchan.domain.entity.DeliveryStatus
import com.woowa.banchan.domain.entity.OrderInfo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class OrderPagingSourceTest {

    private lateinit var orderDao: OrderDao
    private lateinit var orderPagingSource: OrderPagingSource

    @Before
    fun setup() {
        orderDao = mockk(relaxed = true)
        orderPagingSource = OrderPagingSource(orderDao)
    }

    @Test
    fun `페이지 소스 로드 IO로 실패했을 경우`() = runTest {
        val error = IOException("404", Throwable())
        coEvery { orderDao.findAllWithPage(any(), any()) } throws error

        val expectedResult = PagingSource.LoadResult.Error<Int, OrderInfo>(error)

        try {
            orderPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        } catch (e: IOException) {
            Truth.assertThat(expectedResult.throwable.message).isEqualTo(e.message)
        }
    }

    @Test
    fun `페이지 소스 로드 null로 오는 경우`() = runTest {
        coEvery { orderDao.findAllWithPage(any(), any()) } throws NullPointerException()

        val expectedResult = PagingSource.LoadResult.Error<Int, OrderInfo>(NullPointerException())

        try {
            orderPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        } catch (e: NullPointerException) {
            Truth.assertThat(expectedResult.throwable.message).isEqualTo(e.message)
        }
    }

    @Test
    fun `초기 로드 및 새로고침하는 경우`() = runTest {
        val mockList = listOf(
            OrderInfoView(1L, DeliveryStatus.START.status, 1, 3000, "음식1", "123"),
            OrderInfoView(2L, DeliveryStatus.START.status, 3, 2000, "음식2", "456"),
            OrderInfoView(3L, DeliveryStatus.START.status, 5, 1000, "음식3", "789"),
            OrderInfoView(4L, DeliveryStatus.START.status, 6, 900, "음식4", "111"),
            OrderInfoView(5L, DeliveryStatus.START.status, 2, 100, "음식5", "222"),
        )
        val orderInfoMockList = mockList.map { it.toOrderInfo() }
        coEvery { orderDao.findAllWithPage(any(), any()) } returns listOf(mockList[0], mockList[1])

        val expectedResult = PagingSource.LoadResult.Page(
            data = listOf(orderInfoMockList[0], orderInfoMockList[1]),
            prevKey = null,
            nextKey = 1
        )
        val actualResult = orderPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
        println(expectedResult)
        println(actualResult)
        Truth.assertThat(expectedResult).isEqualTo(actualResult)
    }

    @Test
    fun `데이터 페이지를 로드하고 리스트 끝에 추가`() = runTest {
        val mockList = listOf(
            OrderInfoView(1L, DeliveryStatus.START.status, 1, 3000, "음식1", "123"),
            OrderInfoView(2L, DeliveryStatus.START.status, 3, 2000, "음식2", "456"),
            OrderInfoView(3L, DeliveryStatus.START.status, 5, 1000, "음식3", "789"),
            OrderInfoView(4L, DeliveryStatus.START.status, 6, 900, "음식4", "111"),
            OrderInfoView(5L, DeliveryStatus.START.status, 2, 100, "음식5", "222"),
        )
        val orderInfoMockList = mockList.map { it.toOrderInfo() }
        coEvery { orderDao.findAllWithPage(any(), any()) } returns listOf(mockList[0], mockList[1], mockList[2], mockList[3])

        val expectedResult = PagingSource.LoadResult.Page(
            data = listOf(orderInfoMockList[2], orderInfoMockList[3]),
            prevKey = 0,
            nextKey = 2
        )
        val actualResult = orderPagingSource.load(
            PagingSource.LoadParams.Append(
                key = 1,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
        assertThat(actualResult).isNotEqualTo(expectedResult)
    }
}