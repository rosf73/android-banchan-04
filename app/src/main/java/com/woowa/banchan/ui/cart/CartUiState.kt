package com.woowa.banchan.ui.cart

data class CartUiState(
    val cart: List<TestCartItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)