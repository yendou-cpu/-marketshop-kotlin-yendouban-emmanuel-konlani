package com.example.projetgestion1.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetgestion1.data.models.UserProfile
import com.example.projetgestion1.network.RetrofitInstance
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    var nom       by mutableStateOf("")
    var email     by mutableStateOf("")
    var telephone by mutableStateOf("")
    var username  by mutableStateOf("")

    var isLoading    by mutableStateOf(true)
    var errorMessage by mutableStateOf<String?>(null)

    private val prefs = application
        .getSharedPreferences("profile", Context.MODE_PRIVATE)

    init { loadProfile() }

    fun loadProfile() {
        viewModelScope.launch {
            isLoading    = true
            errorMessage = null

            if (prefs.contains("nom")) {
                nom       = prefs.getString("nom",       "") ?: ""
                email     = prefs.getString("email",     "") ?: ""
                telephone = prefs.getString("telephone", "") ?: ""
                username  = prefs.getString("username",  "") ?: ""
                isLoading = false
            } else {
                loadFromApi()
            }
        }
    }

    private suspend fun loadFromApi() {
        try {
            val response = RetrofitInstance.api.getUserProfile()
            if (response.isSuccessful) {
                val user: UserProfile = response.body()!!
                // ✅ user.name.firstname et user.name.lastname existent maintenant
                nom       = "${user.name.firstname} ${user.name.lastname}".trim()
                email     = user.email
                telephone = user.phone
                username  = user.username
                saveToPrefs()
            } else {
                errorMessage = "Impossible de charger le profil"
            }
        } catch (e: Exception) {
            errorMessage = "Erreur réseau : ${e.localizedMessage}"
        } finally {
            isLoading = false
        }
    }

    fun saveProfile() {
        saveToPrefs()
    }

    fun reloadFromApi() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            // ✅ KTX edit {} au lieu de edit().putString().apply()
            prefs.edit().remove("nom").apply()
            loadFromApi()
        }
    }

    fun clearProfileData() {
        prefs.edit().clear().apply()
        nom       = ""
        email     = ""
        telephone = ""
        username  = ""
    }

    private fun saveToPrefs() {
        // ✅ KTX extension : prefs.edit { putString(...) }
        prefs.edit()
            .putString("nom",       nom.trim())
            .putString("email",     email.trim())
            .putString("telephone", telephone.trim())
            .putString("username",  username.trim())
            .apply()
    }
}