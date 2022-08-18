package com.woowa.banchan.data.local

import com.woowa.banchan.data.local.datasource.RecentlyViewedDataSource
import com.woowa.banchan.data.local.entity.RecentlyViewedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRecentlyViewedDataSource(
    private val recentlyViewedList: List<RecentlyViewedEntity>
): RecentlyViewedDataSource {

    override fun getAllRecentlyViewed(): Flow<Result<List<RecentlyViewedEntity>>> {
        return flow { emit(Result.success(recentlyViewedList)) }
    }

    override fun getTop7RecentlyViewed(): Flow<Result<List<RecentlyViewedEntity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun addRecentlyViewed(recentlyViewed: RecentlyViewedEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun modifyRecentlyViewed(recentlyViewed: RecentlyViewedEntity) {
        TODO("Not yet implemented")
    }
}