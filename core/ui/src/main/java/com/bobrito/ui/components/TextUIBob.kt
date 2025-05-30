package com.bobrito.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BobTextHeader (
    text: String = "Welcome to GAIIA",
    color: Color = Color.Black,
    modifier: Modifier = Modifier.padding(16.dp)
) {
    Text(
           text =text,
           modifier = modifier,
           style = TextStyle(
           fontSize = 20.sp,
           fontWeight = FontWeight.Bold,
           lineHeight = 24.sp,
           color = Color.Black,
           textAlign = TextAlign.Left
       ),
        overflow = TextOverflow.Ellipsis,
        color = color,
        maxLines = 1
    )
}

@Composable
fun BobTextFooter () {
    Text(text = "Lupa Password?")
}

@Preview(device = Devices.PIXEL, showSystemUi = true)
@Composable
fun BobTextHeaderPreview () {
    BobTextHeader(
        text = "kembali lgi bersama sy jeremi teti"
    )
}
