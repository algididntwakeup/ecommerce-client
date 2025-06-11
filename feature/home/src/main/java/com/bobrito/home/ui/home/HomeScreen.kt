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
import com.bobrito.home.data.model.Category
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
    onCategorySelected: (Category) -> Unit,
    onNavigateToCart: () -> Unit,
    onSearchClick: () -> Unit
) {
    Column {
        TopBar(title = "Home")

        SearchBar(
            query = "",
            onQueryChange = { /* Handle search */ }
        )

        // Categories Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleLarge
            )
            TextButton(onClick = onCategoriesSeeAll) {
                Text("See All")
            }
        }

        // Categories will be loaded from ViewModel
        // For now showing empty state
        Text(
            text = "No categories available",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
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
                                onProductSelected(item.name)
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
        onSearchClick = {}
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