package com.woowa.banchan.data.remote.dto

data class DetailProductDto(
    val delivery_fee: String,
    val delivery_info: String,
    val detail_section: List<String>,
    val point: String,
    val prices: List<String>,
    val product_description: String,
    val thumb_images: List<String>,
    val top_image: String
)