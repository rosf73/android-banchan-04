package com.woowa.banchan.ui.screen.main.tabs

import com.woowa.banchan.domain.entity.Product

sealed class UiEvent {
    data class ShowToast(val message: String?) : UiEvent()
    data class NavigateToDetail(val product: Product) : UiEvent()
    data class NavigateToCart(val product: Product) : UiEvent()
}