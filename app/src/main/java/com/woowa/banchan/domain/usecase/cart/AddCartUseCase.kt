package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val existCartUseCase: ExistCartUseCase,
    private val modifyCartUseCase: ModifyCartUseCase,
    private val getCartWithHashUseCase: GetCartWithHashUseCase
) {

    operator fun invoke(cart: Cart) = flow {
        existCartUseCase(cart).first()
            .onSuccess { result ->
                when (result) {
                    false -> {
                        cartRepository.addCart(cart)
                        emit(Result.success(true))
                    }
                    true -> {
                        getCartWithHashUseCase(cart.hash).first()
                            .onSuccess {
                                val updateCart = it.copy(quantity = cart.quantity, checked = true)
                                modifyCartUseCase(updateCart)
                                emit(Result.success(true))
                            }
                            .onFailure {
                                emit(Result.failure<Throwable>(NotFoundProductsException()))
                            }
                    }
                }
            }
    }.flowOn(Dispatchers.IO)
}
