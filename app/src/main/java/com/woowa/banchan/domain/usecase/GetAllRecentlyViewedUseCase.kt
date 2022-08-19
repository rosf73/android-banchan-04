package com.woowa.banchan.domain.usecase

import com.woowa.banchan.domain.entity.RecentlyViewed
import com.woowa.banchan.domain.repository.RecentlyViewedRepository
import kotlinx.coroutines.flow.Flow

class GetAllRecentlyViewedUseCase(
    private val recentlyViewedRepository: RecentlyViewedRepository
) {

    suspend operator fun invoke(): Flow<Result<List<RecentlyViewed>>> {
        return recentlyViewedRepository.getAllRecentlyViewed()
    }
}