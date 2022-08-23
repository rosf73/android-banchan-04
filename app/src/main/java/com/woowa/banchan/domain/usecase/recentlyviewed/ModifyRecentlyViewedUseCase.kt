package com.woowa.banchan.domain.usecase.recentlyviewed

import com.woowa.banchan.domain.entity.RecentlyViewed
import com.woowa.banchan.domain.repository.RecentlyViewedRepository
import javax.inject.Inject

class ModifyRecentlyViewedUseCase @Inject constructor(
    private val recentlyViewedRepository: RecentlyViewedRepository
) {

    suspend operator fun invoke(recentlyViewed: RecentlyViewed) {
        return recentlyViewedRepository.modifyRecentlyViewed(recentlyViewed)
    }
}