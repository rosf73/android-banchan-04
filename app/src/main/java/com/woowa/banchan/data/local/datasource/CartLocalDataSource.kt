package com.woowa.banchan.data.local.datasource

import com.woowa.banchan.data.local.dao.CartDao
import com.woowa.banchan.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartLocalDataSource @Inject constructor(
    private val cartDao: CartDao
) : CartDataSource {
    override fun addCart(cartEntity: CartEntity) {
        cartDao.insertCart(cartEntity)
    }

    override fun removeCart(vararg carts: CartEntity) {
        cartDao.deleteCart(*carts)
    }


    override fun getCarts(): Flow<List<CartEntity>> {
        return cartDao.findAll()
    }

    override fun isExistCart(hash: String): Flow<Boolean> {
        return cartDao.existByHash(hash)
    }
}