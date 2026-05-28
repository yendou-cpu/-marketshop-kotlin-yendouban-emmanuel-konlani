package com.example.projetgestion1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetgestion1.data.remote.models.Produits
import com.example.projetgestion1.network.RetrofitInstance
import kotlinx.coroutines.launch

class ProduitsViewModel : ViewModel() {


    private var allProducts: List<Produits> = emptyList()


    var productsList by mutableStateOf<List<Produits>>(emptyList())
        private set


    var categories by mutableStateOf<List<String>>(emptyList())
        private set


    var selectedCategory by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        fetchProducts()
        fetchCategories()
    }

    // Charge tous les produits
    fun fetchProducts() {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val response = RetrofitInstance.api.getProduits()

                if (response.isSuccessful) {
                    allProducts = response.body() ?: emptyList()
                    productsList = allProducts
                } else {
                    errorMessage = "Impossible de récupérer les produits"
                }
            } catch (e: Exception) {
                errorMessage = "Erreur réseau. Vérifiez votre connexion."
            } finally {
                isLoading = false
            }
        }
    }

    // Charge les catégories
    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCategories()
                if (response.isSuccessful) {

                    categories = listOf("Tout") + (response.body() ?: emptyList())
                }
            } catch (_: Exception) {

            }
        }
    }

    // Filtre la liste selon la catégorie choisie
    fun filterByCategory(category: String?) {
        selectedCategory = category
        productsList = if (category == null || category == "Tout") {
            allProducts
        } else {
            allProducts.filter { it.category == category }
        }
    }
}