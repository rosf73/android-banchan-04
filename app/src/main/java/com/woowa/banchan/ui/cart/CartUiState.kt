package com.woowa.banchan.ui.cart

import com.woowa.banchan.domain.entity.Cart

data class CartUiState(
    val cart: MutableList<Cart> = mutableListOf(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)