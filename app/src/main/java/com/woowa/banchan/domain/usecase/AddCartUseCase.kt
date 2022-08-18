package com.woowa.banchan.domain.usecase

import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.exception.ExistCartException
import com.woowa.banchan.domain.repository.CartRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

class AddCartUseCase(
    private val cartRepository: CartRepository
) {

    operator fun invoke(cart: Cart) = flow {
        when (cartRepository.isExistCart(cart.hash).single()) {
            false -> emit(Result.failure<Throwable>(ExistCartException()))
            true -> cartRepository.addCart(cart)
        }
    }
}