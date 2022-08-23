package com.woowa.banchan.ui.screen.cart

import com.woowa.banchan.domain.entity.Cart

data class CartUiState(
    val cart: MutableList<Cart> = mutableListOf(),
    val isLoading: Boolean = true,
    val errorMessage: String = ""
)