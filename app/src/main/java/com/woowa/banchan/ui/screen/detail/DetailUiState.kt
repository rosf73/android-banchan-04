package com.woowa.banchan.ui.screen.detail

import com.woowa.banchan.domain.entity.DetailProduct

data class DetailUiState(
    val product: DetailProduct = DetailProduct.default,
    val isLoading: Boolean = false
)
