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
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDetail by remember { mutableStateOf<Product?>(null) }
    val sheetState = rememberModalBottomSheetState()

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
            } else {
                error = response.error ?: "Unknown error occurred"
            }
        } catch (e: Exception) {
            error = e.message ?: "Failed to fetch products"
        } finally {
            isLoading = false
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
                    .weight(1f)
                    .clickable { },
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
                    JelloTextRegular(
                        modifier = Modifier.weight(1f)
                    )
                    JelloImageViewClick(
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
                        items(products) { product ->
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
            FilterSortBottomSheet(onApplyClick = { showBottomSheet = false })
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
        ProductScreens()
    }
}