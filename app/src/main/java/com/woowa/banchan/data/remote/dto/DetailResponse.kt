package com.woowa.banchan.data.remote.dto

import com.squareup.moshi.Json

data class DetailResponse(
    @Json(name = "hash") val hash: String,
    @Json(name = "data") val detailProduct: DetailProductDto
)