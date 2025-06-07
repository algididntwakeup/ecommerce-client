package com.bobrito.home.ui
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun OrderScreens () {
    ScreenContent(title="Order")
}

@Composable
fun AccountScreens () {
    ScreenContent(title="Account")
}

@Composable
fun ScreenContent(title:String){
    Text(text = title)
}