package com.woowa.banchan.data.remote.datasource

import com.woowa.banchan.data.remote.dto.DetailResponse
import com.woowa.banchan.data.remote.dto.PlanResponse
import com.woowa.banchan.data.remote.dto.ProductsResponse
import com.woowa.banchan.data.remote.network.BanchanService
import com.woowa.banchan.domain.exception.NotFoundProductsException
import javax.inject.Inject

class BanchanRemoteDataSource @Inject constructor(
    private val banchanService: BanchanService
) : BanchanDataSource {
    override suspend fun getPlan(): Result<PlanResponse> {
        val response = banchanService.getPlan()
        if (response.isSuccessful) {
            val plan = response.body() ?: return Result.failure(NotFoundProductsException())
            return Result.success(plan)
        }
        return Result.failure(NotFoundProductsException())
    }

    override suspend fun getProducts(type: String): Result<ProductsResponse> {
        val response = banchanService.getProducts(type)
        if (response.isSuccessful) {
            val products = response.body() ?: return Result.failure(NotFoundProductsException())
            return Result.success(products)
        }
        return Result.failure(NotFoundProductsException())
    }

    override suspend fun getDetailProduct(hash: String): Result<DetailResponse> {
        val response = banchanService.getDetail(hash)
        if (response.isSuccessful) {
            val detailProduct =
                response.body() ?: return Result.failure(NotFoundProductsException())
            return Result.success(detailProduct)
        }
        return Result.failure(NotFoundProductsException())
    }
}
