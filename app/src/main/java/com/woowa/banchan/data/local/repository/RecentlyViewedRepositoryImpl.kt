package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.RecentlyViewedDataSource
import com.woowa.banchan.data.local.entity.RecentlyViewedEntity
import com.woowa.banchan.data.local.entity.toRecentlyViewed
import com.woowa.banchan.domain.entity.RecentlyViewed
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.repository.RecentlyViewedRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecentlyViewedRepositoryImpl @Inject constructor(
    private val recentlyViewedDataSource: RecentlyViewedDataSource
): RecentlyViewedRepository {

    override suspend fun getAllRecentlyViewed(): Flow<Result<List<RecentlyViewed>>> = flow {
        val list = recentlyViewedDataSource.getAllRecentlyViewed().first()
        emit(Result.success(list.map { it.toRecentlyViewed() }))
    }

    override suspend fun getTop7RecentlyViewed(): Flow<Result<List<RecentlyViewed>>> = flow {
        val list = recentlyViewedDataSource.getTop7RecentlyViewed().first()
        emit(Result.success(list.map { it.toRecentlyViewed() }))
    }

    override suspend fun addRecentlyViewed(recentlyViewed: RecentlyViewed) {
        coroutineScope {
            launch {
                val newRecentlyViewed = RecentlyViewedEntity(
                    hash = recentlyViewed.hash,
                    name = recentlyViewed.name,
                    description = recentlyViewed.description,
                    imageUrl = recentlyViewed.imageUrl,
                    nPrice = recentlyViewed.nPrice,
                    sPrice = recentlyViewed.sPrice,
                    viewedAt = recentlyViewed.viewedAt
                )
                recentlyViewedDataSource.addRecentlyViewed(newRecentlyViewed)
            }
        }
    }

    override suspend fun modifyRecentlyViewed(recentlyViewed: RecentlyViewed) {
        coroutineScope {
            launch {
                val newRecentlyViewed = RecentlyViewedEntity(
                    hash = recentlyViewed.hash,
                    name = recentlyViewed.name,
                    description = recentlyViewed.description,
                    imageUrl = recentlyViewed.imageUrl,
                    nPrice = recentlyViewed.nPrice,
                    sPrice = recentlyViewed.sPrice,
                    viewedAt = recentlyViewed.viewedAt
                )
                recentlyViewedDataSource.modifyRecentlyViewed(newRecentlyViewed)
            }
        }
    }
}