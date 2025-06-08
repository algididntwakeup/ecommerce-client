package com.bobrito.shoecommerce.navigator

import android.content.Context
import android.content.Intent
import com.bobrito.home.ui.HomeActivity
import com.bobrito.navigator.Navigator


class AppNavigator @Inject constructor() : Navigator {
    override fun navToFeatureHome(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
    }

    override fun navToFeatureAuth(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
    }
}