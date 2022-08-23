package com.woowa.banchan.ui.screen.orderdetail

import com.woowa.banchan.domain.entity.OrderDetailSection.Order
import com.woowa.banchan.domain.entity.OrderDetailSection.OrderLineItem

data class OrderLineItemUiState(
    val orderLineItemList: Map<Order, List<OrderLineItem>> = mapOf(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)