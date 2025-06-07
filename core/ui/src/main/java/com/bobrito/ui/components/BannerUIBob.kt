package com.bobrito.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bobrito.ui.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerSliderUIBob(
    bannerImages: List<Painter> = emptyList(),
    onClick: (Int) -> Unit = {},
    autoScrollDuration: Long = 3000L // Tambah parameter untuk durasi auto scroll
) {
    val pagerState = rememberPagerState(pageCount = { bannerImages.size })
    val scope = rememberCoroutineScope() // Tambah ini yang hilang

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) { page ->
        Image(
            painter = bannerImages[page],
            contentDescription = "Banner Image $page",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick(page) }
        )
    }

    // Pindah LaunchedEffect ke luar HorizontalPager
    LaunchedEffect(pagerState, bannerImages.size) {
        if (bannerImages.size > 1) { // Cuma auto scroll kalau ada lebih dari 1 image
            while (true) {
                delay(autoScrollDuration)
                val nextPage = (pagerState.currentPage + 1) % bannerImages.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BannerSliderUIBobPreview() {
    // Sample data untuk preview
    val sampleImages = listOf(
        painterResource(id = R.drawable.sample_slide1), // Ganti dengan drawable yang ada
        painterResource(id = R.drawable.sample_slide1),  // Ganti dengan drawable yang ada
        painterResource(id = R.drawable.sample_slide1)  // Ganti dengan drawable yang ada
    )

    BannerSliderUIBob(
        bannerImages = sampleImages,
        onClick = { index ->
            println("Banner $index clicked!")
        }
    )
}
