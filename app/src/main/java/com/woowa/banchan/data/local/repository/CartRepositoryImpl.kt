package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.CartDataSource
import com.woowa.banchan.data.local.entity.toCart
import com.woowa.banchan.data.local.entity.toCartEntity
import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDataSource: CartDataSource
) : CartRepository {
    override suspend fun addCart(cart: Cart) {
        cartDataSource.addCart(cart.toCartEntity())
    }

    override suspend fun updateCart(vararg cart: Cart) {
        cartDataSource.updateCart(*cart.map { it.toCartEntity() }.toTypedArray())
    }

    override suspend fun removeCart(vararg carts: Cart) {
        val cartEntities = carts.map { it.toCartEntity() }.toTypedArray()
        cartDataSource.removeCart(*cartEntities)
    }

    override fun getCarts(): Flow<Map<String, Cart>> {
        return cartDataSource.getCarts().map {
            it.toCart()
                .associateBy { cart -> cart.hash }
        }
    }

    override fun getCart(hash: String): Flow<Cart> {
        return cartDataSource.getCart(hash).map { it.toCart() }
    }

    override fun isExistCart(hash: String): Flow<Boolean> {
        return cartDataSource.isExistCart(hash)
    }
}
