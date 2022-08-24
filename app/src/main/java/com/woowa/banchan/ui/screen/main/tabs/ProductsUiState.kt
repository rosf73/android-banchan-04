package com.woowa.banchan.ui.screen.main.tabs

import com.woowa.banchan.domain.entity.Product

data class ProductsUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false
)
