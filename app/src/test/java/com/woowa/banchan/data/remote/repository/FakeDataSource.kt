package com.woowa.banchan.data.remote.repository

import com.woowa.banchan.data.remote.datasource.BanchanDataSource
import com.woowa.banchan.data.remote.dto.DetailResponse
import com.woowa.banchan.data.remote.dto.PlanResponse
import com.woowa.banchan.data.remote.dto.ProductsResponse
import com.woowa.banchan.domain.exception.NotFoundProductsException

class FakeDataSource(
    private val plan: PlanResponse? = null,
    private val products: ProductsResponse? = null,
    private val details: DetailResponse? = null
) : BanchanDataSource {

    override suspend fun getPlan(): Result<PlanResponse> {
        if (plan != null) {
            return Result.success(plan)
        }
        return Result.failure(NotFoundProductsException())
    }

    override suspend fun getProducts(type: String): Result<ProductsResponse> {
        if (products != null) {
            return Result.success(products)
        }
        return Result.failure(NotFoundProductsException())
    }

    override suspend fun getDetailProduct(hash: String): Result<DetailResponse> {
        if (details != null) {
            return Result.success(details)
        }
        return Result.failure(NotFoundProductsException())
    }
}
