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
    override fun addCart(cart: Cart) {
        cartDataSource.addCart(cart.toCartEntity())
    }

    override fun removeCart(vararg carts: Cart) {
        val cartEntities = carts.map { it.toCartEntity() }.toTypedArray()
        cartDataSource.removeCart(*cartEntities)
    }

    override fun getCarts(): Flow<List<Cart>> {
        return cartDataSource.getCarts().map { it.toCart() }
    }

    override fun isExistCart(hash: String): Flow<Boolean> {
        return cartDataSource.isExistCart(hash)
    }
}