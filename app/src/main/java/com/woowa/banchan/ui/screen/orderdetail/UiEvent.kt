package com.woowa.banchan.ui.screen.orderdetail

sealed class UiEvent {
    data class ShowToast(val message: String?) : UiEvent()
    object Refresh : UiEvent()
    object NavigateToBack : UiEvent()
}