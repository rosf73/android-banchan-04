package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.entity.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addCart(cart: Cart)
    suspend fun updateCart(vararg cart: Cart)
    suspend fun removeCart(vararg carts: Cart)
    fun getCarts(): Flow<Map<String, Cart>>
    fun getCart(hash: String): Flow<Cart>
    fun isExistCart(hash: String): Flow<Boolean>
}
