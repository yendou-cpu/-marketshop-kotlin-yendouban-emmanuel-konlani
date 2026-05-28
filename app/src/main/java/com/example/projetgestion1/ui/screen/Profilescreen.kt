package com.example.projetgestion1.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projetgestion1.ui.components.BottomBar
import com.example.projetgestion1.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {
    var isEditing by remember { mutableStateOf(false) }
    var darkMode by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mon Profil") },
                actions = {
                    // Bouton refresh — recharge depuis l'API
                    if (!isEditing) {
                        IconButton(onClick = {
                            viewModel.reloadFromApi()
                            scope.launch {
                                snackbarHostState.showSnackbar("Profil rechargé depuis l'API")
                            }
                        }) {
                            Icon(Icons.Default.Refresh, "Recharger")
                        }
                    }
                    // Bouton crayon / coche
                    IconButton(onClick = {
                        if (isEditing) {
                            viewModel.saveProfile()
                            scope.launch {
                                snackbarHostState.showSnackbar("✓ Profil mis à jour")
                            }
                        }
                        isEditing = !isEditing
                    }) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Check
                            else Icons.Default.Edit,
                            contentDescription = if (isEditing) "Sauvegarder" else "Modifier"
                        )
                    }
                }
            )
        },
        bottomBar = { BottomBar(navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        when {
            // ── Chargement ────────────────────────────────────
            viewModel.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // ── Erreur réseau ─────────────────────────────────
            viewModel.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.WifiOff,
                            null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            viewModel.errorMessage!!,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { viewModel.reloadFromApi() }) {
                            Text("Réessayer")
                        }
                    }
                }
            }

            // ── Contenu principal ─────────────────────────────
            else -> {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(24.dp))

                    // ── Avatar avec initiales ──────────────────
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = viewModel.nom.split(" ")
                                .mapNotNull { it.firstOrNull()?.toString() }
                                .take(2)
                                .joinToString("")
                                .ifEmpty { "?" },
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    // Nom + username + email sous l'avatar (mode lecture)
                    if (!isEditing) {
                        Text(
                            text = viewModel.nom.ifEmpty { "Utilisateur" },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "@${viewModel.username}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = viewModel.email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(Modifier.height(28.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(20.dp))

                    // ── Informations ───────────────────────────
                    Text(
                        "Informations personnelles",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                    )

                    if (isEditing) {
                        // Mode édition — champs modifiables liés au ViewModel
                        OutlinedTextField(
                            value = viewModel.nom,
                            onValueChange = { viewModel.nom = it },
                            label = { Text("Nom complet") },
                            leadingIcon = { Icon(Icons.Default.Person, null) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(
                            value = viewModel.username,
                            onValueChange = { viewModel.username = it },
                            label = { Text("Nom d'utilisateur") },
                            leadingIcon = { Icon(Icons.Default.AlternateEmail, null) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(
                            value = viewModel.email,
                            onValueChange = { viewModel.email = it },
                            label = { Text("Email") },
                            leadingIcon = { Icon(Icons.Default.Email, null) },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true
                        )
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(
                            value = viewModel.telephone,
                            onValueChange = { viewModel.telephone = it },
                            label = { Text("Téléphone") },
                            leadingIcon = { Icon(Icons.Default.Phone, null) },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true
                        )
                    } else {
                        // Mode lecture
                        InfoRow(Icons.Default.Person, "Nom",
                            viewModel.nom.ifEmpty { "Non renseigné" })
                        InfoRow(Icons.Default.AlternateEmail, "Username",
                            "@${viewModel.username}")
                        InfoRow(Icons.Default.Email, "Email",
                            viewModel.email.ifEmpty { "Non renseigné" })
                        InfoRow(Icons.Default.Phone, "Téléphone",
                            viewModel.telephone.ifEmpty { "Non renseigné" })
                    }

                    Spacer(Modifier.height(24.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(20.dp))

                    // ── Mode sombre ────────────────────────────
                    Text(
                        "Préférences",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = if (darkMode) Icons.Default.DarkMode
                                else Icons.Default.LightMode,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Column {
                                Text("Mode sombre", fontWeight = FontWeight.Medium)
                                Text(
                                    if (darkMode) "Activé" else "Désactivé",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Switch(checked = darkMode, onCheckedChange = { darkMode = it })
                    }

                    Spacer(Modifier.height(24.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(20.dp))

                    OutlinedButton(
                        onClick = { showConfirmDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(Icons.Default.DeleteForever, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Vider mes données")
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }

    //  suppression
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            icon = { Icon(Icons.Default.Warning, null, tint = MaterialTheme.colorScheme.error) },
            title = { Text("Vider mes données ?") },
            text = { Text("Supprime le profil local et le panier. Le profil sera rechargé depuis l'API.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearProfileData()
                        viewModel.reloadFromApi()
                        showConfirmDialog = false
                        scope.launch {
                            snackbarHostState.showSnackbar("Données supprimées — profil API rechargé")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) { Text("Confirmer") }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) { Text("Annuler") }
            }
        )
    }
}

// ── Ligne d'information ────────────────────────────────────
@Composable
fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(22.dp)
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}