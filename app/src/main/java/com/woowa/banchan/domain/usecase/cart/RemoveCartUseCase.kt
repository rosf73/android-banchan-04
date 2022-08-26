package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.repository.CartRepository
import javax.inject.Inject

class RemoveCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    suspend operator fun invoke(vararg cart: Cart) {
        cartRepository.removeCart(*cart)
    }
}
