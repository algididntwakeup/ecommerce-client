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
import com.bobrito.ui.R
import com.bobrito.ui.components.BannerSliderUIBob
import com.bobrito.ui.components.BobImageViewClick
import com.bobrito.ui.components.BobImageViewPhotoUrlRounded
import com.bobrito.ui.components.BobTextRegular
import com.bobrito.ui.theme.BiruPersib
import com.bobrito.ui.theme.Purple40
import com.bobrito.ui.theme.VividMagenta
import com.bobrito.ui.theme.birudongker
import com.bobrito.ui.theme.oranye

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(birudongker) // Background biru dulu
    ) {
        item {
            // Header Section dengan background colored
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(birudongker)
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
                            .clickable {
                                // Handle search click
                            },
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
                                onClick = {
                                    // Handle search icon click
                                }
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
                        onClick = {
                            // Handle cart click
                        }
                    )
                }

                // Banner Slider
                val sampleImages = listOf(
                    painterResource(id = R.drawable.pap1),
                    painterResource(id = R.drawable.pap2),
                    painterResource(id = R.drawable.pap3),
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

        // Content Section dengan rounded corners - INI YANG PENTING!
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)) // Clip di sini
                    .background(Color.White) // Background putih setelah clip
                    .padding(top = 24.dp) // Padding setelah background
            ) {
                val productItems = listOf(
                    ProductItem(
                        "Categories",
                        listOf(
                            "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg",
                            "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg",
                            "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg",
                            "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg "
                        )
                    ),
                    ProductItem(
                        "New Release",
                        listOf(
                            "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg",
                            "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg",
                            "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg",
                            "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg "
                        )
                    ),
                    ProductItem(
                        "Popular Items",
                        listOf(
                            "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg",
                            "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg",
                            "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg",
                            "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg "
                        )
                    )
                )

                ItemProductHomeList(
                    items = productItems,
                    onSeeAllClick = { category ->
                        println("See All clicked for: $category")
                    }
                )

                // Tambah padding bottom biar gak kepotong
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}


data class ProductItem(
    val title: String,
    val subItems: List<String> = emptyList()
)

@Composable
fun ItemProductHomeList(
    items: List<ProductItem>,
    onSeeAllClick: (String) -> Unit = {}
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
                // Bold, elegant typography sesuai guidelines
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    color = Color.Black
                )

                // See All dengan styled text
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Purple40,
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
            SubItemList(subItems = item.subItems)

            // Spacer between sections - lots of whitespace sesuai guidelines
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SubItemList(subItems: List<String>) {
    LazyRow(
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
    ) {
        items(subItems) { item ->
            Card(
                modifier = Modifier
                    .width(140.dp)
                    .height(160.dp)
                    .clickable {
                        // Handle item click
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp // Subtle shadows sesuai guidelines
                ),
                shape = RoundedCornerShape(12.dp) // Consistent rounded corners
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BobImageViewPhotoUrlRounded(
                        url = item,
                        description = "Product image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Product name placeholder
                    Text(
                        text = "Product Name",
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

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun HomeScreensPreview() {
    HomeScreen()
}

@Preview(showBackground = true)
@Composable
fun SubItemListPreview() {
    val temp = listOf(
        "https://picsum.photos/200/200?random=1",
        "https://picsum.photos/200/200?random=2"
    )
    SubItemList(temp)
}