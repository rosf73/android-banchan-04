package com.woowa.banchan.ui.order

import com.woowa.banchan.domain.entity.OrderInfo

data class OrderUiState(
    val orderInfoList: List<OrderInfo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
