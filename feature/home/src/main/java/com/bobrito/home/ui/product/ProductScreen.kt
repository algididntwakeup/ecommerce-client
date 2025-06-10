package com.bobrito.home.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Star
import com.bobrito.ui.components.BobImageViewPhotoUrlRounded
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.SheetState
import androidx.compose.material3.ListItem
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.filled.Check
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.bobrito.home.ui.product.ProductDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreens(
    category: String? = null,
    onBack: () -> Unit = {}
) {
    val showBottomSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var showDetail by remember { mutableStateOf(false) }

    if (showDetail) {
        ProductDetailScreen(
            onBack = { showDetail = false }
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
                text = category ?: "NEW PRODUCT",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter",
                    modifier = Modifier.clickable { showBottomSheet.value = true }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(6) { // Display 6 dummy product cards
                ProductCard(
                    onClick = { showDetail = true }
                )
            }
        }
    }

    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
            sheetState = sheetState,
        ) {
            FilterSortBottomSheet(onApplyClick = { showBottomSheet.value = false })
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
            // Image Placeholder, now using AsyncImage from Coil
            BobImageViewPhotoUrlRounded(
                url = "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg",
                description = "Product Image",
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
                    text = "Iphone x 64 gb white limited edition...",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 0.dp)
                )
                Text(
                    text = "Rp 11.000.000",
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