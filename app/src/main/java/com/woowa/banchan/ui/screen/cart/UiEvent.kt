package com.woowa.banchan.ui.screen.cart

import com.woowa.banchan.domain.entity.OrderDetailSection.Order
import com.woowa.banchan.domain.entity.RecentlyViewed

sealed class UiEvent {
    data class ShowToast(val message: String?) : UiEvent()
    data class OrderProduct(val order: Order) : UiEvent()
    data class NavigateToDetail(val product: RecentlyViewed) : UiEvent()
    object NavigateToRecentlyViewed : UiEvent()
    object NavigateToBack : UiEvent()
}
