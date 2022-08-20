package com.woowa.banchan.data.local.datasource

import com.woowa.banchan.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    suspend fun addCart(cartEntity: CartEntity)
    suspend fun updateCart(vararg cartEntity: CartEntity)
    suspend fun removeCart(vararg carts: CartEntity)
    fun getCarts(): Flow<List<CartEntity>>
    fun getCart(hash: String): Flow<CartEntity>
    fun isExistCart(hash: String): Flow<Boolean>
}