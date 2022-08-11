package com.woowa.banchan.data.remote.dto

import com.squareup.moshi.Json

data class ProductDto(
    @Json(name = "alt") val alt: String,
    @Json(name = "badge") val badge: List<String?>,
    @Json(name = "delivery_type") val deliveryType: List<String>,
    @Json(name = "description") val description: String,
    @Json(name = "detail_hash") val detailHash: String,
    @Json(name = "image") val image: String,
    @Json(name = "n_price") val nPrice: String,
    @Json(name = "s_price") val sPrice: String,
    @Json(name = "title") val title: String
)