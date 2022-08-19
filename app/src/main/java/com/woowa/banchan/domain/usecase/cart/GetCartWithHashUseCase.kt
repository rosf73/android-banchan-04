package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCartWithHashUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    operator fun invoke(hash: String) = flow {
        cartRepository.getCart(hash).collect {
            emit(Result.success(it))
        }
    }.flowOn(Dispatchers.IO)
}