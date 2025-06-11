package com.bobrito.home.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobrito.home.ui.categories.CategoryItem
import com.bobrito.ui.R
import com.bobrito.ui.components.*
import com.bobrito.ui.theme.BiruPersib
import com.bobrito.ui.theme.VividMagenta
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

// Data classes for API responses
data class CategoryResponse(
    val success: Boolean,
    val data: List<Category>,
    val message: String,
    val error: String?
)

data class Category(
    val id: String,
    val name: String,
    val imageUrl: String,
    val createdAt: String,
    val updatedAt: String
)

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

// API Service interfaces
interface CategoryApiService {
    @GET("api/v1/categories")
    suspend fun getCategories(@Header("Authorization") token: String): CategoryResponse
}

interface ProductApiService {
    @GET("api/v1/products")
    suspend fun getProducts(@Header("Authorization") token: String): ProductResponse
}

@Composable
fun HomeScreen(
    onCategoriesSeeAll: () -> Unit,
    onCategorySelected: (String) -> Unit,
    onNavigateToCart: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onProductSelected: (String) -> Unit
) {
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var allProducts by remember { mutableStateOf<List<Product>>(emptyList()) }
    var newReleaseProducts by remember { mutableStateOf<List<Product>>(emptyList()) }
    var popularProducts by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    // Create Retrofit instance
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("https://ecommerce-gaiia-api.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val categoryApiService = remember { retrofit.create(CategoryApiService::class.java) }
    val productApiService = remember { retrofit.create(ProductApiService::class.java) }

    // Fetch categories and products when the screen is first displayed
    LaunchedEffect(Unit) {
        try {
            // Fetch categories
            val categoryResponse = categoryApiService.getCategories("Bearer prakmobile")
            if (categoryResponse.success) {
                categories = categoryResponse.data
            }

            // Fetch products
            val productResponse = productApiService.getProducts("Bearer prakmobile")
            if (productResponse.success) {
                allProducts = productResponse.data

                // Shuffle products and take 7 for new release and popular
                val shuffledProducts = allProducts.shuffled()
                newReleaseProducts = shuffledProducts.take(7)
                popularProducts = shuffledProducts.drop(7).take(7)
            }
        } catch (e: Exception) {
            error = e.message ?: "Failed to fetch data"
        } finally {
            isLoading = false
        }
    }

    val productItems = listOf(
        ProductItem(
            title = "Categories",
            subItems = categories.map { CategoryItem(it.id, it.name, it.imageUrl) },
            itemType = ItemType.CATEGORY
        ),
        ProductItem(
            title = "New Release",
            subItems = newReleaseProducts.map { CategoryItem(it.id, it.productName, it.imageUrl) },
            itemType = ItemType.PRODUCT
        ),
        ProductItem(
            title = "Popular Items",
            subItems = popularProducts.map { CategoryItem(it.id, it.productName, it.imageUrl) },
            itemType = ItemType.PRODUCT
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BiruPersib)
    ) {
        item {
            // Header Section dengan background colored
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BiruPersib)
                    .padding(bottom = 24.dp)
            ) {
                // Search Bar dan Shopping Cart
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = onSearchClick),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BobImageViewClick(
                                color = Color.White,
                                imageVector = Icons.Outlined.Search,
                                onClick = onSearchClick
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            BobTextRegular(
                                text = "Cari barang kamu disini",
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    BobImageViewClick(
                        color = Color.White,
                        imageVector = Icons.Outlined.ShoppingCart,
                        onClick = onNavigateToCart
                    )
                }

                // Banner Slider
                val sampleImages = listOf(
                    painterResource(id = R.drawable.sample1),
                    painterResource(id = R.drawable.sample2),
                    painterResource(id = R.drawable.sample3)
                )

                BannerSliderUIBob(
                    bannerImages = sampleImages,
                    onClick = { index ->
                        // Handle banner click
                        println("Banner $index clicked!")
                    }
                )
            }
        }

        // Content Section dengan rounded corners
        item {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (error != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = error ?: "Unknown error",
                        color = Color.Red
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(Color.White)
                        .padding(top = 24.dp)
                ) {
                    ItemProductHomeList(
                        items = productItems,
                        onSeeAllClick = { category ->
                            if (category == "Categories") {
                                onCategoriesSeeAll()
                            } else {
                                println("See All clicked for: $category")
                            }
                        },
                        onCategorySelected = onCategorySelected,
                        onProductSelected = onProductSelected
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

// Enum untuk membedakan tipe item
enum class ItemType {
    CATEGORY,
    PRODUCT
}

data class ProductItem(
    val title: String,
    val subItems: List<CategoryItem> = emptyList(),
    val itemType: ItemType = ItemType.PRODUCT  // Default adalah product
)

@Composable
fun ItemProductHomeList(
    items: List<ProductItem>,
    onSeeAllClick: (String) -> Unit = {},
    onCategorySelected: (String) -> Unit = {},  // Untuk category click
    onProductSelected: (String) -> Unit = {}    // Untuk product click
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items.forEach { item ->
            // Section Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    color = Color.Black
                )

                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = VividMagenta,
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append("See All")
                    }
                }

                Text(
                    text = annotatedString,
                    modifier = Modifier.clickable {
                        onSeeAllClick(item.title)
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Product Items Row
            SubItemList(
                subItems = item.subItems,
                itemType = item.itemType,
                onCategorySelected = onCategorySelected,
                onProductSelected = onProductSelected
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SubItemList(
    subItems: List<CategoryItem>,
    itemType: ItemType,
    onCategorySelected: (String) -> Unit = {},
    onProductSelected: (String) -> Unit = {}
) {
    LazyRow(
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
    ) {
        items(subItems) { item ->
            Card(
                modifier = Modifier
                    .width(140.dp)
                    .height(160.dp)
                    .clickable {
                        // Berbeda handling berdasarkan tipe item
                        when (itemType) {
                            ItemType.CATEGORY -> {
                                // Untuk category, pass category ID untuk navigate ke products by category
                                onCategorySelected(item.name)
                            }
                            ItemType.PRODUCT -> {
                                // Untuk product, pass product ID untuk navigate ke product detail
                                onProductSelected(item.id)
                            }
                        }
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BobImageViewPhotoUrlRounded(
                        url = item.imageUrl,
                        description = item.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = Color.Black,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onCategoriesSeeAll = {},
        onCategorySelected = {},
        onNavigateToCart = {},
        onSearchClick = {},
        onProductSelected = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SubItemListPreview() {
    val temp = listOf(
        CategoryItem("1", "Sneakers"),
        CategoryItem("2", "Boots")
    )
    SubItemList(
        subItems = temp,
        itemType = ItemType.CATEGORY
    )
}