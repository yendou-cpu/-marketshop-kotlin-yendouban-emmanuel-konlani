package com.example.projetgestion1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navbar() {
    TopAppBar(
        title = {
            Text(
                text = "STYLE-NOVA",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}
@Composable
fun BottomBar(navController: NavController) {

    NavigationBar {

        // HOME
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("home")
            },
            icon = {
                Icon(Icons.Default.Home, contentDescription = "Home")
            },
            label = {
                Text("Accueil")
            }
        )

        // PANIER
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("cart")
            },
            icon = {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
            },
            label = {
                Text("Panier")
            }
        )

        // PROFIL
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("profile")
            },
            icon = {
                Icon(Icons.Default.Person, contentDescription = "Profile")
            },
            label = {
                Text("Profil")
            }
        )
    }
}

@Composable
fun NewsScreen() {

    val categories = listOf(
        "Men's clothing",
        "Jewelery",
        "Electronics",
        "Women's clothing"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .padding(top = 15.dp)
    ) {

        Text(
            text = "NEW NOW",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 20.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 1.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Rechercher",
                modifier = Modifier.padding(end = 10.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(end = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(categories) { category ->

                    CategoryItem(category)
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: String) {

    Text(
        text = category,
        modifier = Modifier
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}