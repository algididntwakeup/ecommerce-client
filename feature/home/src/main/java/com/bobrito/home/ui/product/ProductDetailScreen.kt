package com.bobrito.home.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import com.bobrito.ui.components.BobImageViewProductDetail
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productName: String = "ULTRABOOST 20 SHOES NMD_R1",
    productPrice: String = "Rp150.000",
    productImage: String = "https://images.stockx.com/images/Nike-Air-Zoom-Elite-8-Turquoise-Jade-Volt.png?fit=fill&bg=FFFFFF&w=700&h=500&fm=webp&auto=compress&q=90&dpr=2&trim=color&updated_at=1627415295",
    rating: Int = 5,
    onBack: () -> Unit = {},
    onAddToCart: () -> Unit = {},
    onBuyNow: () -> Unit = {},
    productDescription: String = "Sepatu running terbaru dengan teknologi boost, nyaman dipakai harian maupun olahraga. Upper knit, outsole karet anti slip, warna putih kombinasi biru.",
    relatedProducts: List<Pair<String, String>> = listOf(
        "NMD_R1 SHOES" to "Rp250.000",
        "NMD_R1 SHOES" to "Rp375.000",
        "NMD_R1 SHOES" to "Rp600.000"
    )
) {
    var showOptionSheet by remember { mutableStateOf(false) }
    var optionType by remember { mutableStateOf("") } // "buy" or "cart"
    val sheetState = rememberModalBottomSheetState()
    var showDescriptionScreen by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(8.dp)
    ) {
        // Image & Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable { onBack() }
                            .padding(8.dp)
                    )
                    Row {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            modifier = Modifier.padding(8.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                BobImageViewProductDetail(
                    url = productImage,
                    description = "Product Image"
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Product Name
        Text(
            text = productName,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        // Rating & Review
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            repeat(rating) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = "  SEE REVIEW",
                color = Color(0xFFFF9800),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        // Price
        Text(
            text = productPrice,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
        // Description Button
        if (showDescriptionScreen) {
            ProductDescriptionScreen(
                description = productDescription,
                onBack = { showDescriptionScreen = false }
            )
            return
        }
        OutlinedButton(
            onClick = { showDescriptionScreen = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text("See Description")
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Related Product
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "RELATED PRODUCT",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = "SEE ALL",
                color = Color(0xFFFF9800),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
        LazyRow(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(relatedProducts) { (name, price) ->
                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .padding(horizontal = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        BobImageViewProductDetail(
                            url = productImage,
                            description = "Related Product"
                        )
                        Text(
                            text = name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = price,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        // Add to Cart & Buy Now
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    optionType = "cart"
                    showOptionSheet = true
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("ADD TO CART", color = Color.Black)
            }
            Button(
                onClick = {
                    optionType = "buy"
                    showOptionSheet = true
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3D00))
            ) {
                Text("BUY NOW", color = Color.White)
            }
        }
    }
    if (showOptionSheet) {
        ModalBottomSheet(
            onDismissRequest = { showOptionSheet = false },
            sheetState = sheetState
        ) {
            ProductOptionBottomSheet(onApplyClick = { showOptionSheet = false })
        }
    }
}

@Composable
fun ProductOptionBottomSheet(onApplyClick: () -> Unit) {
    // State untuk voucher code
    val (voucherCode, setVoucherCode) = remember { mutableStateOf("") }
    // State untuk warna
    val colorOptions = listOf(
        Color.LightGray, Color.Blue, Color.Cyan, Color.Magenta, Color.Green, Color.Yellow
    )
    val (selectedColor, setSelectedColor) = remember { mutableStateOf(Color.LightGray) }
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
        OutlinedTextField(
            value = voucherCode,
            onValueChange = setVoucherCode,
            label = { Text("Enter voucher code") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "CHOOSE COLOR",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            colorOptions.forEach { color ->
                ColorCircle(
                    color = color,
                    isSelected = color == selectedColor,
                    onClick = { setSelectedColor(color) }
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onApplyClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3D00))
        ) {
            Text("Apply now", color = Color.White)
        }
    }
}

@Composable
fun ColorCircle(color: Color, isSelected: Boolean = false, onClick: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
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

@Composable
fun ProductDescriptionScreen(description: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable { onBack() }
                    .padding(end = 8.dp)
            )
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun ProductDetailScreenPreview() {
    ProductDetailScreen()
} 