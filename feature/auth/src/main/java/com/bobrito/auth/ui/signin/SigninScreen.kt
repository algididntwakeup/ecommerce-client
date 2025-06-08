package com.bobrito.auth.ui.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bobrito.auth.MainActivity
import com.bobrito.ui.components.BobButtonPrimary
import com.bobrito.ui.components.BobButtonSocmedRow
import com.bobrito.ui.components.BobEditText
import com.bobrito.ui.components.BobImageViewClick
import com.bobrito.ui.components.BobTextHeader
import com.bobrito.ui.components.BobTextRegular
import com.bobrito.ui.components.BobTextRegularwClick
import com.bobrito.ui.components.BobTextViewRow


@Composable
fun SigninScreen(
    navController: NavController = rememberNavController(),
    viewModel: SignInViewModel = hiltViewModel(),

) {

    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        BobImageViewClick(
            onClick = {
                //
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        BobTextHeader(
            text = "Welcome to GAIIA",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        BobTextRegularwClick(
            text = "Please fill your E-Mail & Password to login into your account. ",
            textClick = "Sign Up",
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = {
                navController.navigate(MainActivity.Screen.AuthSignup.route)
            }
        )
        Spacer(modifier = Modifier.height(25.dp))

        BobTextRegular(
            text = "E-mail",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        BobEditText()

        Spacer(modifier = Modifier.height(10.dp))

        BobTextRegular(
            text = "Password",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        BobEditText(
            visualTransformation = PasswordVisualTransformation()
        )


        BobTextViewRow()

        Spacer(modifier = Modifier.height(30.dp))

        BobButtonPrimary(
            onClick = {
                viewModel.onNavigateToHome(context)
            }
        )

        BobButtonSocmedRow(
            onClickFacebook = {},
            onClickGoogle = {},
        )





    }

}

@Preview (showBackground = true, showSystemUi = true, device = Devices.PIXEL_6)
@Composable
fun SigninScreenPreview(

) {
    SigninScreen()
}