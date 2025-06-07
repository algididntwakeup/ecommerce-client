package com.bobrito.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RatingBar(
    rating: Float,
    starCount: Int = 5,
    onRatingBarChanged: (Float) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (i in 1..starCount) {
            val icon = if (i <= rating) {
                Icons.Filled.Star
            } else {
                Icons.Outlined.Star
            }

            Icon(
                imageVector = icon,
                contentDescription = "rating",
                tint = Color(0xFFFFA500), // Light orange color
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onRatingBarChanged(i.toFloat())
                    }
            )
        }
    }
}

@Preview
@Composable
fun RatingBarPreview() {
    RatingBar(
        rating = 3f,
        starCount = 5
    )
}