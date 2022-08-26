package com.woowa.banchan.domain.usecase.recentlyviewed

import com.woowa.banchan.domain.entity.RecentlyViewed
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.repository.RecentlyViewedRepository
import com.woowa.banchan.domain.usecase.cart.GetCartUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAllRecentlyViewedUseCase(
    private val recentlyViewedRepository: RecentlyViewedRepository,
    private val getCartUseCase: GetCartUseCase
) {

    operator fun invoke(): Flow<Result<List<RecentlyViewed>>> = flow {
        getCartUseCase().combine(recentlyViewedRepository.getAllRecentlyViewed()) { carts, products ->
            val productList = products.getOrThrow()
            val cartMap = carts.getOrThrow()

            productList.map { product ->
                if (cartMap.contains(product.hash)) {
                    product.copy(hasCart = true)
                } else {
                    product.copy(hasCart = false)
                }
            }
        }.collect { recentlyViewedList ->
            emit(Result.success(recentlyViewedList))
        }
    }.catch {
        emit(Result.failure(NotFoundProductsException()))
    }.flowOn(Dispatchers.IO)
}
