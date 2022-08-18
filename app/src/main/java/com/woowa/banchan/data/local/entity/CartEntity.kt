package com.woowa.banchan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.woowa.banchan.domain.entity.Cart

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "hash") val hash: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "check") val check: Boolean
)

fun CartEntity.toCart(): Cart = Cart(id, hash, name, imageUrl, quantity, price, check)

fun List<CartEntity>.toCart(): List<Cart> {
    return this.map { it.toCart() }
}

fun Cart.toCartEntity(): CartEntity = CartEntity(
    id = id,
    hash = hash,
    name = name,
    imageUrl = imageUrl,
    quantity = quantity,
    price = price,
    check = checked
)