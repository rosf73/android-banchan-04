package com.woowa.banchan.ui.screen.detail

import com.woowa.banchan.domain.entity.DetailProduct
import com.woowa.banchan.domain.entity.DetailProduct.Companion.BLANK

data class DetailUiState(
    val product: DetailProduct = DetailProduct.default,
    val isLoading: Boolean = false,
    val errorMessage: String = BLANK
)