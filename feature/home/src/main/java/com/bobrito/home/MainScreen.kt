package com.bobrito.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bobrito.home.ui.AccountScreens
import com.bobrito.home.ui.BottomNavItem
import com.bobrito.home.ui.OrderScreens
import com.bobrito.home.ui.categories.CategoriesScreen
import com.bobrito.home.ui.home.HomeScreen
import com.bobrito.home.ui.cart.CartScreen
import com.bobrito.home.ui.product.ProductScreens
import com.bobrito.ui.theme.AbuMonyetGelap

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            bottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavigationGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun bottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Product,
        BottomNavItem.Order,
        BottomNavItem.Account
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = AbuMonyetGelap
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 12.sp,
                            textAlign = TextAlign.Center
                        ),
                        color = AbuMonyetGelap
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (item.route == "order") {
                        navController.navigate("cart") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                alwaysShowLabel = true
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(
                onCategoriesSeeAll = {
                    navController.navigate("categories")
                },
                onCategorySelected = { category ->
                    navController.navigate("product/$category")
                },
                onNavigateToCart = {
                    navController.navigate("cart")
                }
            )
        }
        composable(BottomNavItem.Product.route) {
            ProductScreens(
                onBack = { navController.popBackStack() }
            )
        }
        composable("product/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            ProductScreens(
                category = category,
                onBack = { navController.popBackStack() }
            )
        }
        composable(BottomNavItem.Order.route) { OrderScreens() }
        composable(BottomNavItem.Account.route) { AccountScreens() }

        // Categories screen
        composable("categories") {
            CategoriesScreen(
                onBack = { navController.popBackStack() },
                onCategorySelected = { category ->
                    navController.navigate("product/$category") {
                        popUpTo("categories")
                    }
                }
            )
        }

        // Cart screen
        composable("cart") {
            CartScreen(
                onBack = { navController.popBackStack() },
                onCheckout = {
                    navController.navigate("checkout")
                }
            )
        }
    }
}

@Preview(
    showBackground = true, device = Devices.NEXUS_5
)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
