package com.woowa.banchan.domain.usecase

import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.exception.ExistCartException
import com.woowa.banchan.domain.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddCartUseCase(
    private val cartRepository: CartRepository
) {

    operator fun invoke(cart: Cart) = flow {
        when (cartRepository.isExistCart(cart.hash).first()) {
            false -> cartRepository.addCart(cart)
            true -> emit(Result.failure<Throwable>(ExistCartException()))
        }
    }.flowOn(Dispatchers.IO)
}