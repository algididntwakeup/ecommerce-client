package com.bobrito.home.ui.product

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon

@Composable
fun ProductScreens() {
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
                    .clickable { },
                colors = CardDefaults.cardColors(
                    containerColor = Gray.copy(alpha = 0.1f)
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

@Preview(showSystemUi = true)
@Composable
fun ProductScreensPreview() {
    MaterialTheme {
        ProductScreens()
    }
}