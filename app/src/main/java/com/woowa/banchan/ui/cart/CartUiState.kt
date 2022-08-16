package com.woowa.banchan.ui.cart

import com.woowa.banchan.ui.recently.TestRecently

data class CartUiState(
    val cart: MutableList<TestCartItem> = mutableListOf(),
    val recentlyList: List<TestRecently> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)