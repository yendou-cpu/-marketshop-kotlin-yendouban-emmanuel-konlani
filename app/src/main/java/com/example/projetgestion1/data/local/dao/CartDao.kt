package com.example.projetgestion1.data.local.dao

import androidx.room.*
import com.example.projetgestion1.data.local.entities.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: CartItem)

    @Delete
    suspend fun delete(item: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT * FROM cart_items WHERE productId = :id")
    suspend fun getCartItem(id: Int): CartItem?
}