package com.woowa.banchan.domain.usecase.product

import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.domain.entity.SortType
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.repository.BanchanRepository
import com.woowa.banchan.domain.usecase.cart.GetCartUseCase
import com.woowa.banchan.extensions.toMoneyInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: BanchanRepository,
    private val getCartUseCase: GetCartUseCase
) {
    operator fun invoke(
        type: String,
        sortType: SortType = SortType.Default
    ): Flow<Result<List<Product>>> = flow {
        getCartUseCase().combine(repository.getProducts(type)) { carts, products ->
            val productList = products.getOrThrow()
            val cartMap = carts.getOrThrow()
            productList.map { product ->
                if (cartMap.contains(product.detailHash)) {
                    product.copy(hasCart = true)
                } else {
                    product.copy(hasCart = false)
                }
            }
        }.collect { updateProducts ->
            when (sortType) {
                SortType.Default -> {
                    emit(Result.success(updateProducts))
                }
                SortType.PriceAscending -> {
                    emit(Result.success(updateProducts.sortedByDescending { it.sPrice.toMoneyInt() }))
                }
                SortType.PriceDescending -> {
                    emit(Result.success(updateProducts.sortedBy { it.sPrice.toMoneyInt() }))
                }
                SortType.RateDescending -> {
                    emit(Result.success(updateProducts.sortedByDescending { it.discountRate }))
                }
            }
        }
    }.catch {
        emit(Result.failure(NotFoundProductsException()))
    }.flowOn(Dispatchers.IO)
}
