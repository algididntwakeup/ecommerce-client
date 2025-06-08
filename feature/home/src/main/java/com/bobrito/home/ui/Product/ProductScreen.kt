package com.bobrito.home.ui.Product


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bagicode.ui.components.RatingBar
import com.bobrito.ui.components.BobImageViewClick
import com.bobrito.ui.components.BobImageViewPhotoUrlRounded
import com.bobrito.ui.components.BobTextRegular
import com.bobrito.ui.theme.AbuKebiruan
import com.bobrito.ui.theme.Abuabu
import com.bobrito.ui.theme.OrenGoogle
import com.bobrito.ui.theme.VeryLightGrey


@Composable
fun ProductScreens () {
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
                imageVector = ImageVector.vectorResource(id = com.bobrito.home.R.drawable.ic_katalog_style),
                color = AbuKebiruan
            )
        }


        ItemProduct()
    }
}

@Composable
fun ItemProduct() {
    LazyColumn {
        items (10){
            Row {
                Card (
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 16.dp)
                        .clickable{

                        },
                    colors = CardDefaults.cardColors(
                        containerColor = AbuKebiruan
                    )
                ) {
                    BobImageViewPhotoUrlRounded(
                        url = "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg",
                        description = "produt"
                    )
                }

                Spacer(
                    modifier = Modifier
                        .padding(start = 8.dp)
                )

                Column {
                    BobTextRegular(
                        text = "Nama Produt",
                        modifier = Modifier

                    )
                    BobTextRegular(
                        text = "Rp. 250.000",
                        modifier = Modifier.padding(top = 7.dp),
                        color = OrenGoogle
                    )
                    RatingBar(
                        rating = 2f,
                        modifier = Modifier.padding(top = 18.dp),
                        onRatingChanged = {

                        }
                    )
                }
            }
        }
    }
}

@Preview( showSystemUi = true)
@Composable
fun ProductScreensPreview () {
    ProductScreens()
}