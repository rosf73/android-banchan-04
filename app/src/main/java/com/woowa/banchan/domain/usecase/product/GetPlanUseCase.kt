package com.woowa.banchan.domain.usecase.product

import com.woowa.banchan.domain.entity.Category
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.repository.BanchanRepository
import com.woowa.banchan.domain.usecase.cart.GetCartUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetPlanUseCase @Inject constructor(
    private val repository: BanchanRepository,
    private val getCartUseCase: GetCartUseCase
) {
    operator fun invoke(): Flow<Result<List<Category>>> = flow {
        getCartUseCase().combine(repository.getPlan()) { cart, plan ->
            val categoryList = plan.getOrThrow()
            val cartMap = cart.getOrThrow()
            categoryList.map { category ->
                val productList = category.menus.map { product ->
                    if (cartMap.contains(product.detailHash)) {
                        product.copy(hasCart = true)
                    } else {
                        product.copy(hasCart = false)
                    }
                }
                category.copy(menus = productList)
            }
        }.collect {
            emit(Result.success(it))
        }
    }.catch {
        emit(Result.failure(NotFoundProductsException()))
    }.flowOn(Dispatchers.IO)
}
