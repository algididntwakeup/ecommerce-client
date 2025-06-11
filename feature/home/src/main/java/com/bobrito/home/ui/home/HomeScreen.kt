package com.bobrito.home.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.bobrito.ui.components.BannerSliderUIBob
import com.bobrito.ui.components.BobImageViewClick
import com.bobrito.ui.components.BobImageViewPhotoUrlRounded
import com.bobrito.ui.components.BobTextRegular
import com.bobrito.ui.theme.BiruPersib
import com.bobrito.ui.theme.VividMagenta

@Composable
fun HomeScreen(
    onCategoriesSeeAll: () -> Unit,
    onCategorySelected: (String) -> Unit,
    onNavigateToCart: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    // Define categories list
    val categories = listOf(
        CategoryItem("1", "Sneakers"),
        CategoryItem("2", "Boots"),
        CategoryItem("3", "Sandals"),
        CategoryItem("4", "Flip Flops"),
        CategoryItem("5", "Slippers"),
        CategoryItem("6", "Socks"),
    )

    // Sample products for New Release and Popular Items
    val newReleaseProducts = listOf(
        CategoryItem("7", "New Sneaker 1"),
        CategoryItem("8", "New Boot 1"),
        CategoryItem("9", "New Sandal 1"),
        CategoryItem("10", "New Flip Flop 1")
    )

    val popularProducts = listOf(
        CategoryItem("11", "Popular Sneaker 1"),
        CategoryItem("12", "Popular Boot 1"),
        CategoryItem("13", "Popular Sandal 1"),
        CategoryItem("14", "Popular Flip Flop 1")
    )

    val productItems = listOf(
        ProductItem(
            title = "Categories",
            subItems = categories,
            itemType = ItemType.CATEGORY
        ),
        ProductItem(
            title = "New Release",
            subItems = newReleaseProducts,
            itemType = ItemType.PRODUCT
        ),
        ProductItem(
            title = "Popular Items",
            subItems = popularProducts,
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
                    painterResource(id = R.drawable.sample_slide1),
                    painterResource(id = R.drawable.sample_slide1),
                    painterResource(id = R.drawable.sample_slide1)
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
                    onCategorySelected = onCategorySelected,  // Pass ke function untuk navigate ke products
                    onProductSelected = { product ->
                        // Implementasi navigate ke product detail
                        println("Product $product selected")
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))
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