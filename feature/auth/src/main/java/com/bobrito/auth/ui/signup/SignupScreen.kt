package com.bobrito.auth.ui.signup

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
import com.bobrito.auth.MainActivity
import com.bobrito.home.ui.HomeActivity
import com.bobrito.ui.components.*

@Composable
fun SignupScreen(
    navController: NavController = rememberNavController(),
    viewModel: SignupViewModel = viewModel()
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
                    navController.navigateUp()
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            BobTextHeader(
                text = "Create Account",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            BobTextRegularwClick(
                text = "Already have an account? ",
                textClick = "Sign In",
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = {
                    navController.navigateUp()
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            BobTextRegular(
                text = "Name",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            BobEditText(
                value = viewModel.name,
                onValueChange = viewModel::onNameChange,
                enabled = !viewModel.isLoading
            )

            Spacer(modifier = Modifier.height(10.dp))

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

            Spacer(modifier = Modifier.height(10.dp))

            BobTextRegular(
                text = "Phone Number",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            BobEditText(
                value = viewModel.numberPhone,
                onValueChange = viewModel::onNumberPhoneChange,
                enabled = !viewModel.isLoading,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.height(10.dp))

            BobTextRegular(
                text = "Address",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            BobEditText(
                value = viewModel.address,
                onValueChange = viewModel::onAddressChange,
                enabled = !viewModel.isLoading
            )

            Spacer(modifier = Modifier.height(30.dp))

            BobButtonPrimary(
                text = if (viewModel.isLoading) "Creating account..." else "Create Account",
                enabled = !viewModel.isLoading &&
                        viewModel.name.isNotEmpty() &&
                        viewModel.email.isNotEmpty() &&
                        viewModel.password.isNotEmpty() &&
                        viewModel.numberPhone.isNotEmpty() &&
                        viewModel.address.isNotEmpty(),
                onClick = {
                    viewModel.register {
                        // On successful registration
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
fun SignupScreenPreview() {
    SignupScreen()
}