package com.bobrito.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobrito.ui.theme.VividMagenta

@Composable
fun BobTextHeader(
    text: String = "Welcome to GAIIA",
    color: Color = Color.Black,
    modifier: Modifier = Modifier.padding(16.dp)
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 24.sp,
            color = color, // Fix: pakai parameter color, bukan hardcode
            textAlign = TextAlign.Left
        ),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
fun BobTextFooter() {
    Text(text = "Lupa Password?")
}

@Composable
fun BobTextRegularwClick(
    text: String = "Fill your E-mail & Password to login to your account. ",
    textClick: String = "Sign Up",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier.padding(16.dp),
    textColor: Color = Color.Black,
    clickableColor: Color = VividMagenta
) {
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

    val annotatedText = buildAnnotatedString {
        // Tambahkan text utama
        append(text)

        // Tambahkan anotasi untuk text yang bisa diklik
        pushStringAnnotation(
            tag = "TEXT_CLICK",
            annotation = textClick
        )
        withStyle(
            style = SpanStyle(
                color = clickableColor,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline // Biar keliatan clickable
            )
        ) {
            append(textClick)
        }
        pop()
    }

    Text(
        text = annotatedText,
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures { offset ->
                layoutResult.value?.let { layout ->
                    val position = layout.getOffsetForPosition(offset)
                    annotatedText.getStringAnnotations(
                        tag = "TEXT_CLICK",
                        start = position,
                        end = position
                    ).firstOrNull()?.let {
                        onClick()
                    }
                }
            }
        },
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 24.sp,
            color = textColor,
            textAlign = TextAlign.Left
        ),
        onTextLayout = { layoutResult.value = it }
    )
}

@Preview(device = Devices.PIXEL, showSystemUi = true)
@Composable
fun BobTextHeaderPreview() {
    BobTextHeader(
        text = "kembali lgi bersama sy jeremi teti"
    )
}

@Preview(showBackground = true)
@Composable
fun BobTextRegularwClickPreview() {
    BobTextRegularwClick(
        text = "Don't have an account? ",
        textClick = "Sign Up",
        onClick = {
            println("Sign Up clicked!")
        }
    )
}

@Composable
fun BobTextRegular(
    text: String = "E-mail",
    modifier : Modifier = Modifier.padding(16.dp),
    color: Color = Color.Black
){
Text(
    text = text,
    modifier = modifier,
    style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 16.sp,
            textAlign = TextAlign.Left
    ),
    color = color
)
}

@Preview
@Composable
fun BobTextRegularPreview(){
 BobTextRegular()
}