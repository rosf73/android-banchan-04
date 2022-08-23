package com.woowa.banchan.data.local.datasource

import com.woowa.banchan.data.local.dao.RecentlyViewedDao
import com.woowa.banchan.data.local.entity.RecentlyViewedEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecentlyViewedLocalDataSource @Inject constructor(
    private val recentlyViewedDao: RecentlyViewedDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): RecentlyViewedDataSource {

    override fun getAllRecentlyViewed(): Flow<List<RecentlyViewedEntity>> {
        return recentlyViewedDao.findAllByViewedAtDesc()
    }

    override suspend fun modifyRecentlyViewed(recentlyViewed: RecentlyViewedEntity) = withContext(ioDispatcher) {
        recentlyViewedDao.insertOrUpdate(recentlyViewed)
    }
}