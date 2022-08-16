package com.woowa.banchan.domain.usecase

import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.domain.entity.SortType
import com.woowa.banchan.domain.repository.BanchanRepository
import com.woowa.banchan.extensions.toMoneyInt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: BanchanRepository
) {
    operator fun invoke(type: String, sortType: SortType): Flow<Result<List<Product>>> = flow {
        repository.getProducts(type)
            .onEach { result ->
                result
                    .onSuccess { products ->
                        when (sortType) {
                            SortType.Default -> {
                                emit(Result.success(products))
                            }
                            SortType.PriceAscending -> {
                                emit(Result.success(products.sortedByDescending { it.sPrice.toMoneyInt() }))
                            }
                            SortType.PriceDescending -> {
                                emit(Result.success(products.sortedBy { it.sPrice.toMoneyInt() }))
                            }
                            SortType.RateDescending -> {
                                emit(Result.success(products.sortedByDescending { it.discountRate }))
                            }
                        }
                    }
                    .onFailure { emit(Result.failure(it)) }
            }
            .collect()
    }
}