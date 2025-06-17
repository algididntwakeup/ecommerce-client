package com.bobrito.home.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobrito.ui.components.BobImageViewPhotoUrlRounded
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import java.text.NumberFormat
import java.util.Locale

// Data classes for API response
data class ProductResponse(
    val success: Boolean,
    val data: List<Product>,
    val message: String,
    val error: String?
)

data class Product(
    val id: String,
    val productName: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val categoryId: String,
    val brandId: String,
    val createdAt: String,
    val updatedAt: String
)

// API Service interface
interface ProductApiService {
    @GET("api/v1/products")
    suspend fun getProducts(@Header("Authorization") token: String): ProductResponse
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreens(
    category: String? = null,
    onBack: () -> Unit = {}
) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var filteredProducts by remember { mutableStateOf<List<Product>>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDetail by remember { mutableStateOf<Product?>(null) }
    val sheetState = rememberModalBottomSheetState()
    // Price range filter state
    var minPrice by remember { mutableStateOf("") }
    var maxPrice by remember { mutableStateOf("") }
    var pendingMinPrice by remember { mutableStateOf("") }
    var pendingMaxPrice by remember { mutableStateOf("") }
    // Sort option state
    var sortOption by remember { mutableStateOf("none") } // "none", "termurah", "termahal"
    var pendingSortOption by remember { mutableStateOf(sortOption) }

    // Create Retrofit instance
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("https://ecommerce-gaiia-api.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService = remember { retrofit.create(ProductApiService::class.java) }

    // Fetch products when the screen is first displayed
    LaunchedEffect(Unit) {
        try {
            val response = apiService.getProducts("Bearer prakmobile")
            if (response.success) {
                products = response.data
                filteredProducts = products // Initialize filtered products with all products
            } else {
                error = response.error ?: "Unknown error occurred"
            }
        } catch (e: Exception) {
            error = e.message ?: "Failed to fetch products"
        } finally {
            isLoading = false
        }
    }

    // Filter products based on search query and price range
    LaunchedEffect(searchQuery, products, minPrice, maxPrice, sortOption) {
        val filtered = products.filter { product ->
            val matchesQuery = searchQuery.isEmpty() ||
                product.productName.contains(searchQuery, ignoreCase = true) ||
                product.description.contains(searchQuery, ignoreCase = true)
            val min = minPrice.toIntOrNull() ?: Int.MIN_VALUE
            val max = maxPrice.toIntOrNull() ?: Int.MAX_VALUE
            val matchesPrice = product.price in min..max
            matchesQuery && matchesPrice
        }
        filteredProducts = when (sortOption) {
            "termurah" -> filtered.sortedBy { it.price }
            "termahal" -> filtered.sortedByDescending { it.price }
            else -> filtered
        }
    }

    if (showDetail != null) {
        ProductDetailScreen(
            productName = showDetail!!.productName,
            productPrice = formatPrice(showDetail!!.price),
            productImage = showDetail!!.imageUrl,
            productDescription = showDetail!!.description,
            onBack = { showDetail = null }
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onBack() }
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Gray.copy(alpha = 0.1f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Transparent),
                        placeholder = { Text("Search products...") },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        // Category or New Product header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category ?: "ALL PRODUCT",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter",
                    modifier = Modifier.clickable { showBottomSheet = true }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                error != null -> {
                    Text(
                        text = error ?: "Unknown error",
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(filteredProducts) { product ->
                            ProductCard(
                                product = product,
                                onClick = { showDetail = product }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
        ) {
            FilterSortBottomSheet(
                minPrice = pendingMinPrice,
                maxPrice = pendingMaxPrice,
                onMinPriceChange = { pendingMinPrice = it },
                onMaxPriceChange = { pendingMaxPrice = it },
                sortOption = pendingSortOption,
                onSortOptionChange = { pendingSortOption = it },
                onApplyClick = {
                    minPrice = pendingMinPrice
                    maxPrice = pendingMaxPrice
                    sortOption = pendingSortOption
                    showBottomSheet = false
                }
            )
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
            .clickable { onClick() },
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
fun FilterSortBottomSheet(
    minPrice: String,
    maxPrice: String,
    onMinPriceChange: (String) -> Unit,
    onMaxPriceChange: (String) -> Unit,
    sortOption: String,
    onSortOptionChange: (String) -> Unit,
    onApplyClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "FILTER BY PRICE",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = minPrice,
                onValueChange = onMinPriceChange,
                label = { Text("Min Price") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            OutlinedTextField(
                value = maxPrice,
                onValueChange = onMaxPriceChange,
                label = { Text("Max Price") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "URUTKAN",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = sortOption == "termurah",
                    onClick = { onSortOptionChange("termurah") }
                )
                Text("Termurah")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = sortOption == "termahal",
                    onClick = { onSortOptionChange("termahal") }
                )
                Text("Termahal")
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onApplyClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text("Apply Now", color = Color.Black)
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
        ProductScreens()
    }
}