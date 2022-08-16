package com.woowa.banchan.ui.main.tabs

import com.woowa.banchan.domain.entity.DetailProduct.Companion.BLANK
import com.woowa.banchan.domain.entity.Product

data class ProductsUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = BLANK
)
