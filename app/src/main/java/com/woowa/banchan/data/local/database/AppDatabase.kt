package com.woowa.banchan.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.woowa.banchan.data.local.dao.CartDao
import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.dao.RecentlyViewedDao
import com.woowa.banchan.data.local.entity.CartEntity
import com.woowa.banchan.data.local.entity.OrderEntity
import com.woowa.banchan.data.local.entity.OrderLineItemEntity
import com.woowa.banchan.data.local.entity.RecentlyViewedEntity

@Database(
    entities = [CartEntity::class, OrderEntity::class, OrderLineItemEntity::class, RecentlyViewedEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun recentlyViewedDao(): RecentlyViewedDao

    companion object {
        const val DATABASE_NAME = "banchan.db"
    }
}