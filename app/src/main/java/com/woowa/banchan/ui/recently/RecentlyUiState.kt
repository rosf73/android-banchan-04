package com.woowa.banchan.ui.recently

import com.woowa.banchan.domain.entity.Product

data class RecentlyUiState(
    val recentlyList: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
