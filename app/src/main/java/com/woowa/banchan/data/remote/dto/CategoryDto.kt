package com.woowa.banchan.data.remote.dto

import com.squareup.moshi.Json

data class CategoryDto(
    @Json(name = "category_id") val categoryId: String,
    @Json(name = "items") val products: List<ProductDto>,
    @Json(name = "name") val name: String
)