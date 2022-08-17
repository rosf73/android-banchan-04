package com.woowa.banchan.data.local.dao

import androidx.room.*
import com.woowa.banchan.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun findAll(): Flow<List<CartEntity>>

    @Delete
    suspend fun deleteCart(cartEntity: CartEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cartEntity: CartEntity)

    @Query("SELECT EXISTS(SELECT * FROM cart WHERE id = :id)")
    fun existById(id: Long): Flow<Boolean>
}