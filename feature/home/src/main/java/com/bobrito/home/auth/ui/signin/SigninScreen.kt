package com.bobrito.home.auth.ui.signin

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bobrito.home.auth.MainActivity
import com.bobrito.home.ui.HomeActivity
import com.bobrito.ui.components.*

@Composable
fun SigninScreen(
    navController: NavController = rememberNavController(),
    viewModel: SigninViewModel = viewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.error) {
        viewModel.error?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
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

            BobEditText(
                value = viewModel.email,
                onValueChange = viewModel::onEmailChange,
                enabled = !viewModel.isLoading,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(10.dp))

            BobTextRegular(
                text = "Password",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            BobEditText(
                value = viewModel.password,
                onValueChange = viewModel::onPasswordChange,
                enabled = !viewModel.isLoading,
                visualTransformation = PasswordVisualTransformation()
            )

            BobTextViewRow()

            Spacer(modifier = Modifier.height(30.dp))

            BobButtonPrimary(
                text = if (viewModel.isLoading) "Signing in..." else "Sign In",
                enabled = !viewModel.isLoading && viewModel.email.isNotEmpty() && viewModel.password.isNotEmpty(),
                onClick = {
                    viewModel.login {
                        // On successful login
                        context.startActivity(Intent(context, HomeActivity::class.java))
                        (context as? MainActivity)?.finish()
                    }
                }
            )

            BobButtonSocmedRow(
                onClickFacebook = {},
                onClickGoogle = {},
            )
        }

        if (viewModel.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SigninScreenPreview() {
    SigninScreen()
}