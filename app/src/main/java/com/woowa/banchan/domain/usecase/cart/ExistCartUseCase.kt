package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ExistCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    operator fun invoke(cart: Cart) = flow {
        cartRepository.isExistCart(cart.hash).collect {
            emit(Result.success(it))
        }
    }.flowOn(Dispatchers.IO)
}