package com.woowa.banchan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.woowa.banchan.domain.entity.RecentlyViewed

@Entity(
    tableName = "recently_viewed",
    indices = [Index(value = ["hash"], unique = true)]
)
data class RecentlyViewedEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "hash") val hash: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "n_price") val nPrice: String? = null,
    @ColumnInfo(name = "s_price") val sPrice: String,
    @ColumnInfo(name = "viewed_at") val viewedAt: Long
)

fun RecentlyViewedEntity.toRecentlyViewed(): RecentlyViewed {
    return RecentlyViewed(hash, name, description, imageUrl, nPrice, sPrice, viewedAt)
}
