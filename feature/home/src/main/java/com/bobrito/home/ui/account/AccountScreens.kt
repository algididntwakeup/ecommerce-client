package com.bobrito.home.ui.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bobrito.ui.components.BobImageViewClick
import com.bobrito.ui.components.BobImageViewPhotoUrlRounded
import com.bobrito.ui.components.BobTextRegular
import com.bobrito.ui.theme.BiruPersib
import com.bobrito.ui.theme.birudongker

@Composable
fun AccountScreens() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(birudongker),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BobImageViewClick(
                color = Color.White,
                onClick = {
                    // Handle back navigation
                }
            )

            BobTextRegular(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                text = "Profile",
                color = Color.White
            )

            BobImageViewClick(
                color = Color.White,
                onClick = {
                    // Handle logout
                },
                imageVector = ImageVector.vectorResource(id = com.bobrito.home.R.drawable.ic_logout)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Profile Picture
        BobImageViewPhotoUrlRounded(
            url = "https://graziamagazine.com/wp-content/uploads/2020/07/GettyImages-76214713.jpg?resize=1024%2C1443",
            description = "profile pic",
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Welcome Text
        BobTextRegular(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = "Selamat Datang, Lady Diana!",
            color = Color.White
        )

        Spacer(modifier = Modifier.height(64.dp))

        Spacer(modifier = Modifier.weight(1f))

        // Menu Items - UPDATED ICONS
        val sampleMenu = listOf(
            MenuItem(
                iconLeft = Icons.Default.Person,
                label = "Edit Profile",
                iconRight = Icons.AutoMirrored.Filled.KeyboardArrowRight
            ),
            MenuItem(
                iconLeft = Icons.Default.AddCircle,
                label = "Shipping Address",
                iconRight = Icons.AutoMirrored.Filled.KeyboardArrowRight
            ),
            MenuItem(
                iconLeft = Icons.Outlined.FavoriteBorder, // ❤️ Heart outline untuk Wishlist
                label = "Wishlist",
                iconRight = Icons.AutoMirrored.Filled.KeyboardArrowRight
            ),
            MenuItem(
                iconLeft = Icons.Outlined.DateRange, // 🕐 Jam outline untuk Order History
                label = "Order History",
                iconRight = Icons.AutoMirrored.Filled.KeyboardArrowRight
            ),
            MenuItem(
                iconLeft = Icons.Default.Notifications,
                label = "Notification",
                iconRight = Icons.AutoMirrored.Filled.KeyboardArrowRight
            ),
            MenuItem(
                iconLeft = Icons.Default.ShoppingCart,
                label = "Cards",
                iconRight = Icons.AutoMirrored.Filled.KeyboardArrowRight
            ),
        )

        LazyColumn(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
        ) {
            items(sampleMenu) { menuItem ->
                ItemMenuAccount(
                    iconLeft = menuItem.iconLeft,
                    label = menuItem.label,
                    iconRight = menuItem.iconRight,
                    onClick = {
                        println("${menuItem.label} clicked")
                    }
                )
            }
        }
    }
}

data class MenuItem(
    val iconLeft: ImageVector,
    val label: String,
    val iconRight: ImageVector,
)

@Composable
fun ItemMenuAccount(
    modifier: Modifier = Modifier,
    label: String = "Edit Profile",
    iconLeft: ImageVector = Icons.Default.Person,
    iconRight: ImageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
    colorLeft: Color = Color.Black,
    colorRight: Color = Color.Black,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BobImageViewClick(
                color = colorLeft,
                imageVector = iconLeft,
                imageDescription = "Icon Left",
                onClick = { onClick() }
            )

            BobTextRegular(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = label,
                color = Color.Black
            )

            BobImageViewClick(
                color = colorRight,
                imageVector = iconRight,
                imageDescription = "Icon Right",
                onClick = { onClick() }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountScreensPreview() {
    AccountScreens()
}
