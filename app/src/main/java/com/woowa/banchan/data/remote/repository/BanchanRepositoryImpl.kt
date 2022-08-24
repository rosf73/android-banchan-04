package com.woowa.banchan.data.remote.repository

import com.woowa.banchan.data.remote.datasource.BanchanDataSource
import com.woowa.banchan.data.remote.dto.toCategory
import com.woowa.banchan.data.remote.dto.toDetailProduct
import com.woowa.banchan.data.remote.dto.toProduct
import com.woowa.banchan.domain.entity.Category
import com.woowa.banchan.domain.entity.DetailProduct
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.repository.BanchanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BanchanRepositoryImpl @Inject constructor(
    private val banchanDataSource: BanchanDataSource
) : BanchanRepository {

    override fun getPlan(): Flow<Result<List<Category>>> = flow {
        banchanDataSource.getPlan()
            .onSuccess { plan ->
                emit(Result.success(plan.categories.map { it.toCategory() }))
            }
            .onFailure {
                emit(Result.failure(NotFoundProductsException()))
            }
    }

    override fun getProducts(type: String): Flow<Result<List<Product>>> = flow {
        banchanDataSource.getProducts(type)
            .onSuccess { result ->
                emit(Result.success(result.products.map { it.toProduct() }))
            }
            .onFailure {
                emit(Result.failure(NotFoundProductsException()))
            }
    }

    override fun getDetailProduct(hash: String): Flow<Result<DetailProduct>> = flow {
        banchanDataSource.getDetailProduct(hash)
            .onSuccess { detail ->
                emit(Result.success(detail.toDetailProduct()))
            }
            .onFailure {
                emit(Result.failure(NotFoundProductsException()))
            }
    }
}
