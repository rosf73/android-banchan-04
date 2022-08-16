package com.woowa.banchan.ui.recentlyviewed

import com.woowa.banchan.ui.cart.TestRecently

data class RecentlyUiState(
    val recentlyList: List<TestRecently> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
