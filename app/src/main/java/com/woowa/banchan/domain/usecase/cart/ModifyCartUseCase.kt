package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.repository.CartRepository
import javax.inject.Inject

class ModifyCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    suspend operator fun invoke(vararg cart: Cart) {
        cartRepository.updateCart(*cart)
    }
}