package com.example.projetgestion1.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projetgestion1.data.local.entities.CartItem
import com.example.projetgestion1.ui.components.BottomBar
import com.example.projetgestion1.viewmodel.CartViewModel
import kotlin.collections.isNotEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel = viewModel()
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalPrice = cartViewModel.getTotal(cartItems)
    val totalCount = cartViewModel.getTotalCount(cartItems)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (totalCount > 0) "Mon Panier ($totalCount)" else "Mon Panier",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        bottomBar = {
            Column {
                if (cartItems.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total :", style = MaterialTheme.typography.titleMedium)
                                Text(
                                    text = "${"%.2f".format(totalPrice)} €",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(Modifier.height(16.dp))
                            Button(
                                onClick = { navController.navigate("checkout") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Valider la commande", modifier = Modifier.padding(4.dp))
                            }
                        }
                    }
                }
                BottomBar(navController)
            }
        }
    ) { paddingValues ->

        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🛒", style = MaterialTheme.typography.displayMedium)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Votre panier est vide",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(8.dp))
                    TextButton(onClick = { navController.navigate("home") }) {
                        Text("Parcourir le catalogue")
                    }
                }
            }
        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = cartItems,
                    key = { it.productId }
                ) { item ->
                    CartItemRow(
                        item = item,
                        onIncrease = {
                            cartViewModel.updateQuantity(item, item.quantity + 1)
                        },
                        onDecrease = {
                            cartViewModel.updateQuantity(item, item.quantity - 1)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = item.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${"%.2f".format(item.price)} €",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Sous-total : ${"%.2f".format(item.price * item.quantity)} €",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(onClick = onDecrease) {
                    Icon(
                        imageVector = if (item.quantity == 1) Icons.Default.Delete
                        else Icons.Default.Remove,
                        contentDescription = if (item.quantity == 1) "Supprimer" else "Diminuer",
                        tint = if (item.quantity == 1) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = item.quantity.toString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = onIncrease) {
                    Icon(Icons.Default.Add, contentDescription = "Augmenter")
                }
            }
        }
    }
}