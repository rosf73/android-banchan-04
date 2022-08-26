package com.woowa.banchan.ui.screen.main.tabs.plan

import com.woowa.banchan.domain.entity.Category

data class PlanUiState(
    val plans: List<Category> = emptyList(),
    val isLoading: Boolean = false
)
