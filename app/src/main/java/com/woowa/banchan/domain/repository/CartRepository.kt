package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.entity.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun addCart(cart: Cart)
    fun removeCart(vararg carts: Cart)
    fun getCarts(): Flow<List<Cart>>
    fun isExistCart(hash: String): Flow<Boolean>
}