package com.woowa.banchan.ui.cart

import com.woowa.banchan.domain.entity.Product

data class CartUiState(
    val cart: MutableList<TestCartItem> = mutableListOf(),
    val recentlyList: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)