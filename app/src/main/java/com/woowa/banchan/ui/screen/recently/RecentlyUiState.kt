package com.woowa.banchan.ui.screen.recently

import com.woowa.banchan.domain.entity.RecentlyViewed

data class RecentlyUiState(
    val recentlyList: List<RecentlyViewed> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
