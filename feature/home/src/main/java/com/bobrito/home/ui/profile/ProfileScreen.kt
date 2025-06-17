package com.bobrito.home.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bobrito.home.ui.components.BobImageProfile
import com.bobrito.home.auth.data.local.SessionManager

import com.bobrito.home.ui.components.BobProfileMenuItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onEditProfile: () -> Unit,
    onShippingAddress: () -> Unit,
    onWishlist: () -> Unit,
    onOrderHistory: () -> Unit,
    onLogout: () -> Unit,
    customer: com.bobrito.home.auth.data.model.Customer?,
    onLoggedOut: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0047))
    ) {
        // Top Bar
        TopAppBar(
            title = { Text("ACCOUNT", color = Color.White) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    onLogout()
                    onLoggedOut()
                }) {
                    Icon(
                        Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

        // Profile Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BobImageProfile(
                onClick = onEditProfile
            )

            Spacer(modifier = Modifier.height(16.dp))

            BobImageProfile(name = customer?.name ?: "Admin")
        }

        // Menu Items
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Edit Profile
                BobProfileMenuItem(
                    icon = Icons.Default.Person,
                    title = "Edit Profile",
                    onClick = onEditProfile
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Shipping Address
                BobProfileMenuItem(
                    icon = Icons.Outlined.LocationOn,
                    title = "Shipping Address",
                    onClick = onShippingAddress
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Wishlist
                BobProfileMenuItem(
                    icon = Icons.Outlined.Favorite,
                    title = "Wishlist",
                    onClick = onWishlist
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Order History
                BobProfileMenuItem(
                    icon = Icons.Outlined.History,
                    title = "Order History",
                    onClick = onOrderHistory
                )
            }
        }
    }
}