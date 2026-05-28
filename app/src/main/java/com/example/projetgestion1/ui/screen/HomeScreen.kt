package com.example.projetgestion1.ui.screen

import com.example.projetgestion1.viewmodel.CartViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage

import com.example.projetgestion1.data.remote.models.Produits
import com.example.projetgestion1.ui.components.BottomBar
import com.example.projetgestion1.ui.components.Navbar
import com.example.projetgestion1.viewmodel.ProduitsViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ProduitsViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Scaffold(
        topBar = { Navbar() },
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // ── Filtres par catégorie ──────────────────────────
            if (viewModel.categories.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.categories) { cat ->
                        val isSelected = when {
                            cat == "Tout" -> viewModel.selectedCategory == null
                            else -> cat == viewModel.selectedCategory
                        }
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                viewModel.filterByCategory(if (cat == "Tout") null else cat)
                            },
                            label = { Text(cat.replaceFirstChar { it.uppercase() }) }
                        )
                    }
                }
                HorizontalDivider()
            }

            // ── Corps ─────────────────────────────────────────
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    viewModel.isLoading -> CircularProgressIndicator()

                    viewModel.errorMessage != null -> Text(
                        text = viewModel.errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )

                    viewModel.productsList.isEmpty() -> Text("Aucun produit trouvé")

                    else -> {
                        // Grille 2 colonnes
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(viewModel.productsList) { produit ->
                                ProduitCard(
                                    produit = produit,
                                    onClick = {
                                        // ✅ Navigue vers le détail en passant l'id
                                        navController.navigate("detail/${produit.id}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProduitCard(produit: Produits, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            AsyncImage(
                model = produit.image,
                contentDescription = produit.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = produit.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = produit.category.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "${"%.2f".format(produit.price)} €",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}