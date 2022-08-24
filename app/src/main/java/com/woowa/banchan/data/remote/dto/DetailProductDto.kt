package com.woowa.banchan.data.remote.dto

import com.squareup.moshi.Json

data class DetailProductDto(
    @Json(name = "delivery_fee") val deliveryFee: String,
    @Json(name = "delivery_info") val deliveryInfo: String,
    @Json(name = "detail_section") val detailSection: List<String>,
    @Json(name = "point") val point: String,
    @Json(name = "prices") val prices: List<String>,
    @Json(name = "product_description") val productDescription: String,
    @Json(name = "thumb_images") val thumbImages: List<String>,
    @Json(name = "top_image") val topImage: String
)
