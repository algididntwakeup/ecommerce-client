package com.bobrito.home.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bobrito.home.auth.data.local.SessionManager
import com.bobrito.home.auth.ui.signin.SigninScreen
import com.bobrito.home.auth.ui.signin.SigninViewModel
import com.bobrito.home.auth.ui.signup.SignupScreen
import com.bobrito.home.ui.HomeActivity

class MainActivity : ComponentActivity() {

    sealed class Screen(val route: String) {
        object AuthSignin : Screen("auth/signin")
        object AuthSignup : Screen("auth/signup")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if user is already logged in
        val sessionManager = SessionManager(this)
        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            com.bobrito.ui.theme.ShoecommerceappTheme {
                val navController = rememberNavController()
                val signinViewModel: SigninViewModel = viewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.AuthSignin.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.AuthSignin.route) {
                            SigninScreen(
                                navController = navController,
                                viewModel = signinViewModel
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

