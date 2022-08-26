package com.woowa.banchan.data.local.cart

import com.woowa.banchan.data.local.datasource.CartDataSource
import com.woowa.banchan.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCartDataSource(
    cartList: List<CartEntity>
) : CartDataSource {

    private var cartListTemp = cartList

    override suspend fun addCart(cartEntity: CartEntity) {
        cartListTemp = cartListTemp.toMutableList().apply { add(cartEntity) }
    }

    override suspend fun updateCart(vararg cartEntity: CartEntity) {
        val tempNewEntities = cartEntity.sortedBy { it.id }
        val tempOldEntities = cartListTemp.sortedBy { it.id }
        var i = 0
        cartListTemp = tempOldEntities.map { entity ->
            if (entity.id == tempNewEntities[i].id) {
                i++
                tempNewEntities[i - 1]
            } else {
                entity
            }
        }
    }

    override suspend fun removeCart(vararg carts: CartEntity) {
        TODO("Not yet implemented")
    }

    override fun getCarts(): Flow<List<CartEntity>> {
        return flow { emit(cartListTemp) }
    }

    override fun getCart(hash: String): Flow<CartEntity> {
        TODO("Not yet implemented")
    }

    override fun isExistCart(hash: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}
