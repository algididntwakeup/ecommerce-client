package com.bobrito.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bobrito.ui.R
import com.bobrito.ui.theme.AbuMonyetGelap
import com.bobrito.ui.theme.BiruFesbuk
import com.bobrito.ui.theme.LightOrange
import com.bobrito.ui.theme.OrenGoogle

@Composable
fun BobButtonPrimary(
    text: String = "Login Now",
    onClick: () -> Unit = {}
) {
    BobBaseButton(
        text = text,
        onClick = onClick,
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(56.dp),
        color = ButtonDefaults.buttonColors( // Parameter name benar: "color"
            containerColor = LightOrange,
            contentColor = AbuMonyetGelap
        )
    )
}

@Composable
fun BobButtonFacebook(
    text: String = "Facebook",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .height(56.dp)
) {
    BobwIconBaseButton(
        text = text,
        onClick = onClick,
        enabled = true,
        modifier = modifier,
        buttonColors = ButtonDefaults.buttonColors( // Parameter name benar: "buttonColors"
            containerColor = BiruFesbuk,
            contentColor = Color.White
        ),
        srcIcon = R.drawable.ic_fesbuk,
        descIcon = "Facebook"
    )
}

@Composable
fun BobButtonGoogle(
    text: String = "Google",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .height(56.dp),
) {
    BobwIconBaseButton(
        text = text,
        onClick = onClick,
        enabled = true,
        modifier = modifier,
        buttonColors = ButtonDefaults.buttonColors( // Parameter name benar: "buttonColors" reffering dr baseuibob
            containerColor = OrenGoogle,
            contentColor = Color.White
        ),
        srcIcon = R.drawable.ic_gugel,
        descIcon = "Google"
    )
}

@Composable
fun BobButtonSocmedRow(
    onClickGoogle: () -> Unit = {},
    onClickFacebook: () -> Unit = {},
    modifier: Modifier = Modifier.fillMaxWidth()
        .padding(16.dp)
){
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){

        BobButtonGoogle(
            modifier = Modifier
                .height(56.dp)
                .weight(1f),
            onClick = onClickGoogle
        )

        BobButtonFacebook(
            modifier = Modifier
                .height(56.dp)
                .weight(1f),
            onClick = onClickFacebook
        )
    }
}

//Kompilasi Preview

@Preview()
@Composable
fun BobButtonPrimaryPreview() {
    BobButtonPrimary()
}

@Preview()
@Composable
fun BobButtonFacebookPreview() {
    BobButtonFacebook()
}

@Preview()
@Composable
fun BobButtongGooglePreview() {
    BobButtonGoogle()
}

@Preview
@Composable
fun BobButtonSocmedRowPreview(){
    BobButtonSocmedRow()
}

