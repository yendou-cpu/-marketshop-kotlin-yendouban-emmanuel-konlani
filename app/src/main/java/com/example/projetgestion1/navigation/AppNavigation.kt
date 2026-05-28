package com.example.projetgestion1.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projetgestion1.ui.screen.CartScreen
import com.example.projetgestion1.ui.screen.DetailScreen
import com.example.projetgestion1.ui.screen.HomeScreen
import com.example.projetgestion1.ui.screen.LoginScreen
import com.example.projetgestion1.ui.screen.ProfileScreen
import com.example.projetgestion1.ui.screen.SignupScreen
import com.example.projetgestion1.viewmodel.CartViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()


    val cartViewModel: CartViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController)
        }

        composable("signup") {
            SignupScreen(navController)
        }


        composable("home") {
            HomeScreen(
                navController = navController,
                cartViewModel = cartViewModel

            )
        }


        composable(
            route = "detail/{productId}",
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            DetailScreen(
                productId = productId,
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        // Écran panier
        composable("cart") {
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        composable("profile") {
            ProfileScreen(navController = navController)
        }


        composable("checkout") {
            // CheckoutScreen(navController, cartViewModel)
        }
    }
}