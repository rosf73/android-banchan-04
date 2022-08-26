package com.woowa.banchan.ui.navigator

import com.woowa.banchan.domain.entity.Product

interface OnItemCartClickListener {

    fun navigateToCart(product: Product)
}
