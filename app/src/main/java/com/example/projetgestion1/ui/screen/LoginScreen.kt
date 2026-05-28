package com.example.projetgestion1.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projetgestion1.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Connexion",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (viewModel.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    viewModel.login(username, password) {

                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Se connecter")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        if (viewModel.message.isNotEmpty()) {
            Text(
                text = viewModel.message,
                color = if (viewModel.message.contains("réussie"))
                    Color(0xFF2E7D32)
                else
                    MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { navController.navigate("signup") }
        ) {
            Text("Pas encore de compte ? S'inscrire")
        }
    }
}