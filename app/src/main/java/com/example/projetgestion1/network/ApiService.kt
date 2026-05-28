package com.example.projetgestion1.network

import com.example.projetgestion1.data.remote.models.LoginRequest
import com.example.projetgestion1.data.remote.models.Produits
import com.example.projetgestion1.data.remote.models.SignupRequest
import com.example.projetgestion1.data.remote.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {


    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<User>


    @POST("users")
    suspend fun signup(
        @Body request: SignupRequest
    ): Response<User>

    // Tous les produits
    @GET("products")
    suspend fun getProduits(): Response<List<Produits>>

    // Détail d'un produit par id
    @GET("products/{id}")
    suspend fun getProduitById(@Path("id") id: Int): Response<Produits>

    // Liste des catégories
    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>

    // Produits d'une catégorie spécifique
    @GET("products/category/{name}")
    suspend fun getProduitsByCategory(
        @Path("name") category: String
    ): Response<List<Produits>>

    // GET /users/1 — récupère les infos de l'utilisateur connecté
    @GET("users/1")
    suspend fun getUserProfile(): Response<com.example.projetgestion1.data.models.UserProfile>

}