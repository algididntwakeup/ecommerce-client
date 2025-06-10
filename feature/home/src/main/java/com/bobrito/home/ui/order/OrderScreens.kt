package com.bobrito.home.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bobrito.ui.components.BobImageViewClick
import com.bobrito.ui.components.BobImageViewPhotoUrlRounded
import com.bobrito.ui.components.BobTextRegular
import com.bobrito.ui.theme.AbuKebiruan
import com.bobrito.ui.theme.Abuabu
import com.bobrito.ui.theme.OrenGoogle
import com.bobrito.ui.theme.VeryLightGrey
import com.bobrito.ui.theme.oranye

@Composable
fun OrderScreens () {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable{

                    },
                colors = CardDefaults.cardColors(
                    containerColor = Abuabu.copy(alpha = 0.1f)
                )
            ) {

                Row (
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ){
                    BobTextRegular(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically)
                            .weight(1f),
                        text = "Cari Produk Disini",
                        color = Abuabu
                    )

                    BobImageViewClick(
                        color = Abuabu,
                        onClick = {

                        },
                        imageVector = Icons.Outlined.Search

                    )
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 2.dp,
            color = VeryLightGrey
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            BobTextRegular(
                text = "Latest Product",
                modifier = Modifier
                    .weight(1f)
            )

            BobImageViewClick(
                imageVector = ImageVector.vectorResource(id = com.bobrito.home.R.drawable.filter),
                color = AbuKebiruan
            )

            BobImageViewClick(
                imageVector = ImageVector.vectorResource(id = com.bobrito.home.R.drawable.ic_katalog_more),
                color = AbuKebiruan
            )
        }
        ItemProductGrid()
    }
}

@Composable
fun ItemProductGrid(){
    LazyVerticalGrid(
        GridCells.Fixed(2),
        modifier = Modifier.background(Color.White),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        items (10) {
            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{

                        },
                    colors = CardDefaults.cardColors(
                        containerColor = AbuKebiruan
                    )
                ) {
                    BobImageViewPhotoUrlRounded(
                        url = "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg",
                        description = "pap"
                    )
                }
                BobTextRegular(
                    text = "Nama Produt",
                    modifier = Modifier.padding(top = 11.dp)
                )
                BobTextRegular(
                    text = "Rp. 250.000",
                    modifier = Modifier.padding(top = 9.dp),
                    color = oranye
                )
            }
        }
    }
}


@Preview (showBackground = true,
    device = Devices.PIXEL_5)
@Composable
fun OrderScreensPreview () {
    OrderScreens()
}