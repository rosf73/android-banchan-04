package com.woowa.banchan.ui

import com.woowa.banchan.domain.entity.Product

interface OnItemCartClickListener {

    fun navigateToCart(product: Product)
}