package com.bobrito.home.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobrito.ui.components.BobImageViewPhotoUrlRounded
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bobrito.home.data.model.CartItem
import com.bobrito.home.ui.components.*

@Composable
fun CartScreen(
    onBackClick: () -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        TopBar(
            title = "Shopping Cart",
            onBackClick = onBackClick
        )

        when {
            uiState.isLoading -> LoadingSpinner()
            uiState.error != null -> ErrorMessage(
                message = uiState.error!!,
                onRetry = { /* Implement retry */ }
            )
            uiState.items.isEmpty() -> EmptyCart()
            else -> CartContent(
                items = uiState.items,
                totalPrice = uiState.totalPrice,
                onRemoveItem = viewModel::removeFromCart,
                onUpdateQuantity = viewModel::updateQuantity
            )
        }
    }
}

@Composable
private fun EmptyCart() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun CartContent(
    items: List<CartItem>,
    totalPrice: Double,
    onRemoveItem: (String) -> Unit,
    onUpdateQuantity: (String, Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items) { item ->
                CartItemCard(
                    item = item,
                    onRemove = { onRemoveItem(item.id) },
                    onUpdateQuantity = { quantity -> onUpdateQuantity(item.id, quantity) }
                )
            }
        }

        CartSummary(totalPrice = totalPrice)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartItemCard(
    item: CartItem,
    onRemove: () -> Unit,
    onUpdateQuantity: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier
                    .size(80.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Rp ${"%,.0f".format(item.price)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { 
                                if (item.quantity > 1) {
                                    onUpdateQuantity(item.quantity - 1)
                                }
                            }
                        ) {
                            Text("-")
                        }
                        Text(
                            text = item.quantity.toString(),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        IconButton(
                            onClick = { onUpdateQuantity(item.quantity + 1) }
                        ) {
                            Text("+")
                        }
                    }

                    TextButton(onClick = onRemove) {
                        Text("Remove")
                    }
                }
            }
        }
    }
}

@Composable
private fun CartSummary(totalPrice: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Rp ${"%,.0f".format(totalPrice)}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Implement checkout */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Proceed to Checkout")
            }
        }
    }
}

data class CartItem(
    val name: String,
    val price: String,
    val quantity: Int
)