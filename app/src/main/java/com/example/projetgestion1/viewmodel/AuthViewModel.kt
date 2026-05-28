package com.example.projetgestion1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetgestion1.data.remote.models.LoginRequest
import com.example.projetgestion1.data.remote.models.SignupRequest
import com.example.projetgestion1.network.RetrofitInstance
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set
    var message by mutableStateOf("")
        private set


    fun login(username: String, password: String, onSuccess: () -> Unit) {

        if (username.isBlank() || password.isBlank()) {
            message = "Veuillez remplir tous les champs"
            return
        }

        viewModelScope.launch {
            try {
                isLoading = true
                message = ""

                val response = RetrofitInstance.api.login(
                    LoginRequest(username, password)
                )

                if (response.isSuccessful) {
                    message = "Connexion réussie"
                    onSuccess()
                } else {
                    message = "Identifiants incorrects"
                }

            } catch (e: Exception) {
                message = "Erreur de connexion. Vérifiez votre réseau."
            } finally {

                isLoading = false
            }
        }
    }

    fun signup(username: String, email: String, password: String, onSuccess: () -> Unit) {

        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            message = "Veuillez remplir tous les champs"
            return
        }

        if (password.length < 6) {
            message = "Le mot de passe doit contenir au moins 6 caractères"
            return
        }

        viewModelScope.launch {
            try {
                isLoading = true
                message = ""

                val response = RetrofitInstance.api.signup(
                    SignupRequest(username, email, password)
                )

                if (response.isSuccessful) {
                    message = "Compte créé avec succès"
                    onSuccess()
                } else {
                    message = "Erreur lors de la création du compte"
                }

            } catch (e: Exception) {
                message = "Erreur de connexion. Vérifiez votre réseau."
            } finally {
                isLoading = false
            }
        }
    }
}