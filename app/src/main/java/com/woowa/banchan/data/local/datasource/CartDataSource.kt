package com.woowa.banchan.data.local.datasource

import com.woowa.banchan.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    fun addCart(cartEntity: CartEntity)
    fun removeCart(vararg carts: CartEntity)
    fun getCarts(): Flow<List<CartEntity>>
    fun isExistCart(hash: String): Flow<Boolean>
}