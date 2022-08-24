package com.woowa.banchan.ui.screen.order

sealed class UiEvent {
    data class ShowToast(val message: String?) : UiEvent()
    data class NavigateToOrderDetail(val id: Long) : UiEvent()
    object NavigateToBack : UiEvent()
}