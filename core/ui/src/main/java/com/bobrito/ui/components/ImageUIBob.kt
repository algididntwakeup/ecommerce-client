package com.bobrito.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bobrito.ui.theme.VeryLightGrey

@Composable
fun BobImageViewClick(
    onClick: () -> Unit = {},
    color: Color = Color.Black,
    imageVector: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    imageDescription: String = "Back",
    modifier: Modifier = Modifier.size(24.dp)
) {
    IconButton(onClick = { onClick() }) {
        Icon(
            imageVector = imageVector,
            contentDescription = imageDescription,
            modifier = modifier,
            tint = color
        )
    }
}

@Composable
fun BobImageViewPhotoUrlRounded(
    url: String?,
    description: String,
    modifier: Modifier = Modifier // Tambah parameter modifier
) {
    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = url)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                    .transformations()
                    .build()
            })
            .build()
    )

    val state = painter.state

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier // Pakai modifier parameter
    ) {
        // Show loading untuk Loading DAN Error state (seperti yang lama)
        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
            CircularProgressIndicator(
                color = VeryLightGrey
            )
        }

        Image(
            painter = painter,
            contentDescription = description,
            modifier = Modifier
                .size(100.dp) // Keep fixed size seperti yang lama
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BobImageViewClickPreview() {
    BobImageViewClick()
}

@Preview(showBackground = true)
@Composable
fun BobImageViewPhotoUrlRoundedPreview() {
    BobImageViewPhotoUrlRounded(
        url = "https://pbs.twimg.com/profile_images/964099345086689280/wekXLWht_400x400.jpg",
        description = "Pokemon 1"
    )
}