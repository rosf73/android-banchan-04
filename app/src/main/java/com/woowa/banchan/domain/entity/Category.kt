package com.woowa.banchan.domain.entity

data class Category(
    val title: String,
    val menus: List<Product>
)