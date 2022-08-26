package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.RecentlyViewedDataSource
import com.woowa.banchan.data.local.entity.RecentlyViewedEntity
import com.woowa.banchan.data.local.entity.toRecentlyViewed
import com.woowa.banchan.domain.entity.RecentlyViewed
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.repository.RecentlyViewedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecentlyViewedRepositoryImpl @Inject constructor(
    private val recentlyViewedDataSource: RecentlyViewedDataSource
) : RecentlyViewedRepository {

    override fun getAllRecentlyViewed(): Flow<Result<List<RecentlyViewed>>> = flow {
        recentlyViewedDataSource.getAllRecentlyViewed()
            .collect { list ->
                emit(Result.success(list.map { it.toRecentlyViewed() }))
            }
    }.catch {
        emit(Result.failure(NotFoundProductsException()))
    }

    override suspend fun modifyRecentlyViewed(recentlyViewed: RecentlyViewed) {
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
