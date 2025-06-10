package com.bobrito.navigator

import android.content.Context

interface Navigator {

    fun navToFeatureHome(context: Context)

    fun navToFeatureAuth(context: Context)
}