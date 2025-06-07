package com.bobrito.home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.Surface
import com.bobrito.home.MainScreen

class HomeActivity : ComponentActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            com.bobrito.ui.theme.ShoecommerceappTheme {
                Surface {
                    MainScreen()
                }
            }

        }
    }
}

