package com.woowa.banchan.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.entity.toOrderInfo
import com.woowa.banchan.domain.entity.OrderInfo

open class OrderPagingSource(
    private val orderDao: OrderDao
) : PagingSource<Int, OrderInfo>() {

    override fun getRefreshKey(state: PagingState<Int, OrderInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OrderInfo> {
        val position = params.key ?: INIT_PAGE
        val loadData = orderDao.findAllWithPage(position, params.loadSize)

        return LoadResult.Page(
            data = loadData.map { it.toOrderInfo() },
            prevKey = if (position == INIT_PAGE) null else position - 1,
            nextKey = if (loadData.isEmpty()) null else position + 1
        )
    }

    private companion object {
        const val INIT_PAGE = 0
    }
}