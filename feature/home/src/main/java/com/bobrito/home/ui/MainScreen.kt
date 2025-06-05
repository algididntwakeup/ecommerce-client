package com.bobrito.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bobrito.ui.components.BobButtonPrimary
import com.bobrito.ui.components.BobEditText
import com.bobrito.ui.components.BobImageViewClick
import com.bobrito.ui.components.BobTextHeader
import com.bobrito.ui.components.BobTextRegular
import com.bobrito.ui.components.BobTextRegularwClick


@Composable
fun SignupScreen(
    navController: NavController = rememberNavController()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .background(Color.White)
    ) {
        BobImageViewClick(
            onClick = {
                navController.popBackStack()
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        BobTextHeader(
            text = "Create your account",
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        BobTextRegularwClick(
            text = "Do you already have account ?",
            textClick = " Sign In",
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = {
                navController.popBackStack()
            }

        )

        Spacer(modifier = Modifier.height(25.dp))

        BobTextRegular(
            text = "Username",
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        BobEditText(
            value = "Username"
        )

        Spacer(modifier = Modifier.height(10.dp))

        BobTextRegular(
            text = "E-mail",
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        BobEditText()

        Spacer(modifier = Modifier.height(20.dp))

        BobTextRegular(
            text = "Password",
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        BobEditText(
            visualTransformation = PasswordVisualTransformation()
        )

        BobButtonPrimary(
            text = "Create account"
        )

    }

}

@Preview (showBackground = true, showSystemUi = true, device = Devices.PIXEL_6)
@Composable
fun SignupScreenPreview(

) {
    SignupScreen()
}