package com.example.projetgestion1.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projetgestion1.data.local.entities.CartItem

import com.example.projetgestion1.data.remote.models.Produits
import com.example.projetgestion1.network.RetrofitInstance
import com.example.projetgestion1.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    productId: Int,
    navController: NavController,
    cartViewModel: CartViewModel = viewModel()
) {
    var produit by remember { mutableStateOf<Produits?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var quantity by remember { mutableStateOf(1) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        try {
            val response = RetrofitInstance.api.getProduitById(productId)
            if (response.isSuccessful) {
                produit = response.body()
            }
        } catch (e: Exception) {
            // erreur réseau silencieuse
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détail produit") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Retour")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()

                produit == null -> Text(
                    "Produit introuvable",
                    color = MaterialTheme.colorScheme.error
                )

                else -> {
                    val p = produit!!

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        // Image grande
                        AsyncImage(
                            model = p.image,
                            contentDescription = p.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(Modifier.height(16.dp))

                        // Catégorie
                        Text(
                            text = p.category.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(Modifier.height(4.dp))

                        // Titre
                        Text(
                            text = p.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(8.dp))

                        // Prix
                        Text(
                            text = "${"%.2f".format(p.price)} €",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(Modifier.height(16.dp))
                        HorizontalDivider()
                        Spacer(Modifier.height(16.dp))

                        // Description
                        Text(
                            text = p.description,
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 22.sp
                        )

                        Spacer(Modifier.height(24.dp))

                        // Sélecteur de quantité
                        Text("Quantité :", fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            IconButton(
                                onClick = { if (quantity > 1) quantity-- },
                                enabled = quantity > 1
                            ) {
                                Icon(Icons.Default.Remove, "Moins")
                            }

                            Text(
                                text = quantity.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            IconButton(onClick = { quantity++ }) {
                                Icon(Icons.Default.Add, "Plus")
                            }
                        }

                        Spacer(Modifier.height(24.dp))

                        // Bouton Ajouter au panier
                        Button(
                            onClick = {
                                val cartItem = CartItem(
                                    productId = p.id,
                                    title = p.title,
                                    price = p.price,
                                    image = p.image,
                                    category = p.category,
                                    quantity = quantity
                                )
                                cartViewModel.addToCart(cartItem)

                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "✓ $quantity article(s) ajouté(s) au panier"
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Ajouter — ${"%.2f".format(p.price * quantity)} €"
                            )
                        }

                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}