package com.bobrito.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bobrito.home.data.model.Product
import com.bobrito.home.ui.cart.CartScreen
import com.bobrito.home.ui.category.CategoryScreen
import com.bobrito.home.ui.product.ProductScreen

sealed class Screen(val route: String) {
    object Products : Screen("products")
    object Categories : Screen("categories")
    object Cart : Screen("cart")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
}

@Composable
fun HomeNavigation(
    navController: NavHostController,
    startDestination: String = Screen.Products.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Products.route) {
            ProductScreen(
                onProductClick = { product ->
                    navController.navigate(Screen.ProductDetail.createRoute(product.id))
                }
            )
        }

        composable(Screen.Categories.route) {
            CategoryScreen(
                onCategoryClick = { category ->
                    // Navigate to products filtered by category
                    navController.navigate(Screen.Products.route)
                }
            )
        }

        composable(Screen.Cart.route) {
            CartScreen(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.ProductDetail.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            // Implement ProductDetailScreen here
        }
    }
} 