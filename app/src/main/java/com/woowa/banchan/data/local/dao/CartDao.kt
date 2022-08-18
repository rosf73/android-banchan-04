package com.woowa.banchan.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.woowa.banchan.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun finAll(): Flow<List<CartEntity>>
    /*
         findAll() : Flow<List<*>>
         deleteById()
         updateById()
         insert()
         existById()
     */
}