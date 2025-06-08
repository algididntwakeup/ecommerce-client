package com.bobrito.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bobrito.auth.ui.signin.SignInViewModel
import com.bobrito.auth.ui.signin.SigninScreen
import com.bobrito.auth.ui.signup.SignupScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val signInViewModel : SignInViewModel by viewModels()

    sealed class  Screen(val route: String) {
        object AuthSignin : Screen("auth/signin")
        object AuthSignup : Screen("auth/signup")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            com.bobrito.ui.theme.ShoecommerceappTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.AuthSignin.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.AuthSignin.route) {
                            SigninScreen(
                                navController = navController,
                                viewModel = signInViewModel
                            )
                        }
                        composable(Screen.AuthSignup.route) {
                            SignupScreen(
                                navController = navController

                            )
                        }
                    }
                }
            }
        }
    }
}

