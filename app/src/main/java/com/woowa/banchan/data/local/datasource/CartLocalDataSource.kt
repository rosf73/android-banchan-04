package com.woowa.banchan.data.local.datasource

import com.woowa.banchan.data.local.dao.CartDao
import com.woowa.banchan.data.local.entity.CartEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartLocalDataSource @Inject constructor(
    private val cartDao: CartDao
) : CartDataSource {
    override suspend fun addCart(cartEntity: CartEntity) {
        withContext(Dispatchers.IO) {
            cartDao.insertCart(cartEntity)
        }
    }

    override suspend fun updateCart(vararg cartEntity: CartEntity) {
        withContext(Dispatchers.IO) {
            cartDao.updateCart(*cartEntity)
        }
    }

    override suspend fun removeCart(vararg carts: CartEntity) {
        withContext(Dispatchers.IO) {
            cartDao.deleteCart(*carts)
        }
    }

    override fun getCarts(): Flow<List<CartEntity>> {
        return cartDao.findAll()
    }

    override fun getCart(hash: String): Flow<CartEntity> {
        return cartDao.findByHash(hash)
    }

    override fun isExistCart(hash: String): Flow<Boolean> {
        return cartDao.existByHash(hash)
    }
}