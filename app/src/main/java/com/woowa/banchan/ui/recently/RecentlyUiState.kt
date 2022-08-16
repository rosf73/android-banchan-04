package com.woowa.banchan.ui.recently

data class RecentlyUiState(
    val recentlyList: List<TestRecently> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
