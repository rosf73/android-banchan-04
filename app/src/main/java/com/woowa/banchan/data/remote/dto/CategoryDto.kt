package com.woowa.banchan.data.remote.dto

import com.squareup.moshi.Json
import com.woowa.banchan.domain.entity.Category

data class CategoryDto(
    @Json(name = "category_id") val categoryId: String,
    @Json(name = "items") val products: List<ProductDto>,
    @Json(name = "name") val name: String
)

fun CategoryDto.toCategory(): Category {
    return Category(
        title = name,
        menus = products.map { it.toProduct() }
    )
}
