package com.woowa.banchan.data.remote.datasource

import com.woowa.banchan.data.remote.dto.DetailResponse
import com.woowa.banchan.data.remote.dto.PlanResponse
import com.woowa.banchan.data.remote.dto.ProductsResponse

interface BanchanDataSource {

    suspend fun getPlan(): Result<PlanResponse>

    suspend fun getProducts(type: String): Result<ProductsResponse>

    suspend fun getDetailProduct(hash: String): Result<DetailResponse>
}
