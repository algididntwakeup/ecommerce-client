package com.bobrito.home.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bobrito.ui.components.BobImageViewPhotoUrlRounded
import coil.compose.AsyncImage
import java.text.NumberFormat
import java.util.Locale
import com.bobrito.home.data.model.Product
import com.bobrito.home.ui.components.*

// Data classes for API response
data class ProductResponse(
    val success: Boolean,
    val data: List<Product>,
    val message: String,
    val error: String?
)

// API Service interface
interface ProductApiService {
    @GET("api/v1/products")
    suspend fun getProducts(@Header("Authorization") token: String): ProductResponse
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    onProductClick: (Product) -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        TopBar(title = "Products")
        
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = { viewModel.updateSearchQuery(it) }
        )

        FilterSection(
            selectedPriceRange = uiState.selectedPriceRange,
            onPriceRangeSelected = { viewModel.updatePriceRange(it) },
            sortOrder = uiState.sortOrder,
            onSortOrderSelected = { viewModel.updateSortOrder(it) }
        )

        when {
            uiState.isLoading -> LoadingSpinner()
            uiState.error != null -> ErrorMessage(
                message = uiState.error!!,
                onRetry = { /* Implement retry */ }
            )
            else -> ProductGrid(
                products = uiState.filteredProducts,
                onProductClick = onProductClick
            )
        }
    }
}

@Composable
private fun FilterSection(
    selectedPriceRange: PriceRange?,
    onPriceRangeSelected: (PriceRange?) -> Unit,
    sortOrder: SortOrder,
    onSortOrderSelected: (SortOrder) -> Unit
) {
    var showPriceRangeDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = { showPriceRangeDialog = true }) {
            Text(text = selectedPriceRange?.name?.replace("_", " ") ?: "Price Range")
        }

        Row {
            IconButton(onClick = { onSortOrderSelected(SortOrder.LOWEST_PRICE) }) {
                Icon(
                    imageVector = Icons.Default.ArrowUpward,
                    contentDescription = "Sort by lowest price",
                    tint = if (sortOrder == SortOrder.LOWEST_PRICE) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = { onSortOrderSelected(SortOrder.HIGHEST_PRICE) }) {
                Icon(
                    imageVector = Icons.Default.ArrowDownward,
                    contentDescription = "Sort by highest price",
                    tint = if (sortOrder == SortOrder.HIGHEST_PRICE) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

    if (showPriceRangeDialog) {
        AlertDialog(
            onDismissRequest = { showPriceRangeDialog = false },
            title = { Text("Select Price Range") },
            text = {
                Column {
                    PriceRange.values().forEach { range ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { 
                                    onPriceRangeSelected(
                                        if (range == selectedPriceRange) null else range
                                    )
                                    showPriceRangeDialog = false
                                }
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(range.name.replace("_", " "))
                            if (range == selectedPriceRange) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected"
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = { }
        )
    }
}

@Composable
private fun ProductGrid(
    products: List<Product>,
    onProductClick: (Product) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { product ->
            ProductCard(product = product, onClick = { onProductClick(product) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.productName,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = product.productName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                PriceText(price = product.price)
            }
        }
    }
}

@Composable
fun JelloTextRegular(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Cari Barang Kamu Disini",
        modifier = modifier
    )
}

@Composable
fun JelloImageViewClick(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search",
        modifier = modifier.clickable { }
    )
}

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BobImageViewPhotoUrlRounded(
                url = product.imageUrl,
                description = product.productName,
                modifier = Modifier
                    .weight(0.3f)
                    .padding(vertical = 40.dp)
            )
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = product.productName,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 0.dp)
                )
                Text(
                    text = formatPrice(product.price),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Row(
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    repeat(5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.padding(end = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun formatPrice(price: Int): String {
    val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return format.format(price.toDouble())
}

@Composable
fun FilterSortBottomSheet(onApplyClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "SPECIAL OFFER",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Free Delivery checkbox
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Handle click */ },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Free Delivery")
            Checkbox(checked = true, onCheckedChange = { /* Handle change */ })
        }
        // Discount checkbox
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Handle click */ },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Discount")
            Checkbox(checked = false, onCheckedChange = { /* Handle change */ })
        }
        // Special Product checkbox
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Handle click */ },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Special Product")
            Checkbox(checked = false, onCheckedChange = { /* Handle change */ })
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "CHOOSE COLOR",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Spacing between colors
        ) {
            // Example colors
            ColorCircle(color = Color.LightGray, isSelected = true) // Selected color
            ColorCircle(color = Color.Blue)
            ColorCircle(color = Color.Cyan)
            ColorCircle(color = Color.Magenta)
            ColorCircle(color = Color.Green)
            ColorCircle(color = Color.Yellow)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onApplyClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray) // Light gray button
        ) {
            Text("Apply Now", color = Color.Gray) // Gray text
        }
    }
}

@Composable
fun ColorCircle(color: Color, isSelected: Boolean = false) {
    Box(
        modifier = Modifier
            .size(40.dp) // Size of the color circle
            .clip(CircleShape)
            .background(color)
            .clickable { /* Handle color selection */ }
            .then(if (isSelected) Modifier.border(2.dp, Color.Gray, CircleShape) else Modifier)
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProductScreensPreview() {
    MaterialTheme {
        ProductScreen()
    }
}