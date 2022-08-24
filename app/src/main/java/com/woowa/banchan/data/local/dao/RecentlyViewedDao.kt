package com.woowa.banchan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.woowa.banchan.data.local.entity.RecentlyViewedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentlyViewedDao {

    @Query("SELECT * FROM recently_viewed ORDER BY viewed_at DESC")
    fun findAllByViewedAtDesc(): Flow<List<RecentlyViewedEntity>>

    @Query("SELECT * FROM recently_viewed WHERE hash = :hash")
    suspend fun findRecentlyViewedByHash(hash: String): List<RecentlyViewedEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecentlyViewed(recentlyViewed: RecentlyViewedEntity): Long

    @Update
    suspend fun updateRecentlyViewed(recentlyViewed: RecentlyViewedEntity)

    @Transaction
    suspend fun insertOrUpdate(recentlyViewed: RecentlyViewedEntity) {
        val res = findRecentlyViewedByHash(recentlyViewed.hash)
        if (res.isEmpty()) {
            insertRecentlyViewed(recentlyViewed)
        } else {
            updateRecentlyViewed(recentlyViewed.copy(id = res[0].id))
        }
    }
}
