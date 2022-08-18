package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.entity.RecentlyViewed
import kotlinx.coroutines.flow.Flow

interface RecentlyViewedRepository {

    suspend fun getAllRecentlyViewed(): Flow<Result<List<RecentlyViewed>>>

    suspend fun getTop7RecentlyViewed(): Flow<Result<List<RecentlyViewed>>>

    suspend fun addRecentlyViewed(recentlyViewed: RecentlyViewed)

    suspend fun modifyRecentlyViewed(recentlyViewed: RecentlyViewed)
}