package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.entity.Category
import com.woowa.banchan.domain.entity.DetailProduct
import com.woowa.banchan.domain.entity.Product
import kotlinx.coroutines.flow.Flow

interface BanchanRepository {

    fun getPlan(): Flow<Result<List<Category>>>

    fun getProducts(type: String): Flow<Result<List<Product>>>

    fun getDetailProduct(hash: String): Flow<Result<DetailProduct>>
}
