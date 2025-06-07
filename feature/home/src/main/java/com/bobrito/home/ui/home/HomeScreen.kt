package com.bobrito.home.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bobrito.ui.components.BobImageViewClick
import com.bobrito.ui.components.BobTextRegular
import com.bobrito.ui.theme.BiruPersib

@Composable
fun HomeScreen () {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BiruPersib)
    ) {

        Row (
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()

        ){
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable{

                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.2f)
                )

            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                BobImageViewClick(
                    color = Color.White,
                    imageVector = Icons.Outlined.Search,
                    onClick = {

                    }
                )

                BobTextRegular(
                    text = "Cari barang kamu disini",
                    color = Color.White
                )
                    }
            }
            BobImageViewClick(
                color = Color.White,
                imageVector = Icons.Outlined.ShoppingCart,
                onClick = {

                }
            )
        }



    }

    }

@Preview (showBackground = true, showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun HomeScreensPreview () {
    HomeScreen()
}
