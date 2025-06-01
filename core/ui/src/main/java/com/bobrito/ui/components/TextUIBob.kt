package com.bobrito.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobrito.ui.theme.HijauBetawi
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
            color = color,
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

@Composable
fun BobTextRegular(
    text: String = "E-mail",
    modifier: Modifier = Modifier.padding(16.dp),
    color: Color = Color.Black
) {
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

// Di BobTextViewRow component
@Composable
fun BobTextViewRow(
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    onTextClick: () -> Unit = {},
    textLeft: String = "Remember Me",
    textRight: String = "Forgot Password?",
    modifier: Modifier = Modifier // Tambah parameter modifier
) {
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

    Row(
        modifier = modifier // Pakai parameter modifier, hapus internal padding
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Checkbox dengan label "Remember Me"
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = HijauBetawi,
                    uncheckedColor = HijauBetawi.copy(alpha = 0.6f)
                )
            )
            Text(
                text = textLeft,
                modifier = Modifier.padding(start = 4.dp),
                style = TextStyle(fontSize = 14.sp)
            )
        }

        // Clickable text "Forgot password?"
        val annotatedString = buildAnnotatedString {
            pushStringAnnotation(
                tag = "FORGOT_PASSWORD",
                annotation = "forgot_password_click"
            )
            withStyle(
                style = SpanStyle(
                    color = VividMagenta,
                    fontWeight = FontWeight.Medium,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(textRight)
            }
            pop()
        }

        Text(
            text = annotatedString,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        layoutResult.value?.let { layout ->
                            val position = layout.getOffsetForPosition(offset)
                            annotatedString.getStringAnnotations(
                                tag = "FORGOT_PASSWORD",
                                start = position,
                                end = position
                            ).firstOrNull()?.let {
                                onTextClick()
                            }
                        }
                    }
                },
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 12.sp
            ),
            onTextLayout = { layoutResult.value = it }
        )
    }
}



// Previews
@Preview()
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

@Preview(showBackground = true)
@Composable
fun BobTextRegularPreview() {
    BobTextRegular()
}

@Preview(showBackground = true)
@Composable
fun BobTextViewRowPreview() {
    BobTextViewRow(
        onTextClick = {
            println("Forgot password clicked!")
        }
    )
}
