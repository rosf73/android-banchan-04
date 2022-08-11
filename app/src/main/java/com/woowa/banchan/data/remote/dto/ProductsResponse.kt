package com.woowa.banchan.data.remote.dto

import com.squareup.moshi.Json

data class ProductsResponse(
    @Json(name = "statusCode") val statusCode: Int,
    @Json(name = "body") val products: List<ProductDto>
)