package com.woowa.banchan.data.remote.dto

import com.squareup.moshi.Json
import com.woowa.banchan.domain.entity.DetailProduct

data class DetailResponse(
    @Json(name = "hash") val hash: String,
    @Json(name = "data") val detailProduct: DetailProductDto
)

fun DetailResponse.toDetailProduct(): DetailProduct {
    return DetailProduct(
        hash = hash,
        thumbs = detailProduct.thumbImages,
        sPrice = if (detailProduct.prices.size < 2) detailProduct.prices[0] else detailProduct.prices[1],
        nPrice = if (detailProduct.prices.size < 2) null else detailProduct.prices[0],
        point = detailProduct.point,
        deliveryInfo = detailProduct.deliveryInfo,
        deliveryFee = detailProduct.deliveryFee,
        section = detailProduct.detailSection
    )
}