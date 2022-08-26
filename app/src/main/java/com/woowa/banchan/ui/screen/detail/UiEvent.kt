package com.woowa.banchan.ui.screen.detail

import com.woowa.banchan.domain.entity.DetailProduct

sealed class UiEvent {
    data class ShowToast(val message: String?) : UiEvent()
    data class AddToCart(val product: DetailProduct) : UiEvent()
    object NavigateToCart : UiEvent()
    object NavigateToOrder : UiEvent()
}
