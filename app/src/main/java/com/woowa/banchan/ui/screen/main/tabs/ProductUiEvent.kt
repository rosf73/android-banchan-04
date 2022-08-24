package com.woowa.banchan.ui.screen.main.tabs

sealed class ProductUiEvent<out T : Any> {
    data class ShowToast(val message: String?) : ProductUiEvent<Nothing>()
    data class NavigateToDetail<out T : Any>(val data: T) : ProductUiEvent<T>()
    data class NavigateToCart<out T : Any>(val data: T) : ProductUiEvent<T>()
    object NavigateToBack : ProductUiEvent<Nothing>()
}
