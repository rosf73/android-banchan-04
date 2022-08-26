package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.repository.CartRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    operator fun invoke() = flow {
        cartRepository.getCarts().collect {
            emit(Result.success(it))
        }
    }.catch {
        emit(Result.failure(NotFoundProductsException()))
    }
}
