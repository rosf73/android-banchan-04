package com.woowa.banchan.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.woowa.banchan.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun findAll(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart WHERE hash = :hash")
    fun findByHash(hash: String): Flow<CartEntity>

    @Update
    fun updateCart(vararg cartEntity: CartEntity)

    @Delete
    suspend fun deleteCart(vararg cartEntity: CartEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCart(vararg cartEntity: CartEntity)

    @Query("SELECT EXISTS(SELECT * FROM cart WHERE hash = :hash)")
    fun existByHash(hash: String): Flow<Boolean>
}
