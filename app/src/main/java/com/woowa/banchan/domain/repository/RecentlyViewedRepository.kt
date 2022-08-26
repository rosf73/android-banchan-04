package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.entity.RecentlyViewed
import kotlinx.coroutines.flow.Flow

interface RecentlyViewedRepository {

    fun getAllRecentlyViewed(): Flow<Result<List<RecentlyViewed>>>

    suspend fun modifyRecentlyViewed(recentlyViewed: RecentlyViewed)
}
