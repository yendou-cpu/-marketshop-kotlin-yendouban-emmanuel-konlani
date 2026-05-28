package com.example.projetgestion1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetgestion1.data.local.AppDatabase
import com.example.projetgestion1.data.local.entities.CartItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    // Récupération du DAO Room
    private val cartDao = AppDatabase.getInstance(application).cartDao()

    // Liste du panier observée automatiquement
    val cartItems: StateFlow<List<CartItem>> =
        cartDao.getAllCartItems()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // Calcul du prix total
    fun getTotal(items: List<CartItem>): Double {
        return items.sumOf { it.price * it.quantity }
    }

    // Nombre total d'articles
    fun getTotalCount(items: List<CartItem>): Int {
        return items.sumOf { it.quantity }
    }

    // Ajouter au panier
    fun addToCart(item: CartItem) {
        viewModelScope.launch {

            val existing = cartDao.getCartItem(item.productId)

            if (existing != null) {

                cartDao.insertOrUpdate(
                    existing.copy(
                        quantity = existing.quantity + 1
                    )
                )

            } else {

                cartDao.insertOrUpdate(item)
            }
        }
    }

    // Modifier quantité
    fun updateQuantity(item: CartItem, newQty: Int) {

        viewModelScope.launch {

            if (newQty <= 0) {

                cartDao.delete(item)

            } else {

                cartDao.insertOrUpdate(
                    item.copy(quantity = newQty)
                )
            }
        }
    }

    // Supprimer un produit
    fun removeFromCart(item: CartItem) {

        viewModelScope.launch {
            cartDao.delete(item)
        }
    }

    // Vider le panier
    fun clearCart() {

        viewModelScope.launch {
            cartDao.clearCart()
        }
    }
}