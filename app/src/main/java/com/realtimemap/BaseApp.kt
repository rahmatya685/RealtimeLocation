package com.realtimemap

import androidx.multidex.MultiDexApplication
import com.mapbox.mapboxsdk.Mapbox

open
class BaseApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance(this, getString(R.string.access_token))

    }
}