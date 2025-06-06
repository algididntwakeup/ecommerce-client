package com.bobrito.home.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector

) {
    object Home: BottomNavItem("home","Home", Icons.Outlined.Home)

    object Product: BottomNavItem("product","Product", Icons.Outlined.ShoppingCart)

    object Order: BottomNavItem("order","Order", Icons.AutoMirrored.Outlined.List)

    object Account: BottomNavItem("account","Account", Icons.Outlined.Person)


}