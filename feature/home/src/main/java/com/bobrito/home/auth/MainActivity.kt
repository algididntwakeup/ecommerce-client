package com.bobrito.home.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
<<<<<<< HEAD:feature/auth/src/main/java/com/bobrito/auth/MainActivity.kt
import com.bobrito.auth.ui.signin.SignInViewModel
import com.bobrito.auth.ui.signin.SigninScreen
import com.bobrito.auth.ui.signup.SignupScreen
import dagger.hilt.android.AndroidEntryPoint
=======
import com.bobrito.home.auth.data.local.SessionManager
import com.bobrito.home.auth.ui.signin.SigninScreen
import com.bobrito.home.auth.ui.signin.SigninViewModel
import com.bobrito.home.auth.ui.signup.SignupScreen
import com.bobrito.home.ui.HomeActivity
>>>>>>> feature-search:feature/home/src/main/java/com/bobrito/home/auth/MainActivity.kt

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

<<<<<<< HEAD:feature/auth/src/main/java/com/bobrito/auth/MainActivity.kt
    private val signInViewModel : SignInViewModel by viewModels()

    sealed class  Screen(val route: String) {
=======
    sealed class Screen(val route: String) {
>>>>>>> feature-search:feature/home/src/main/java/com/bobrito/home/auth/MainActivity.kt
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
<<<<<<< HEAD:feature/auth/src/main/java/com/bobrito/auth/MainActivity.kt
                                viewModel = signInViewModel
=======
                                viewModel = signinViewModel
>>>>>>> feature-search:feature/home/src/main/java/com/bobrito/home/auth/MainActivity.kt
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

