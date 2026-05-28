package com.example.projetgestion1.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val productId: Int,
    val title: String,
    val price: Double,
    val image: String,
    val category: String,
    val quantity: Int = 1
)