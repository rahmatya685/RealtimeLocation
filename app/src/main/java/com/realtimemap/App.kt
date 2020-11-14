package com.realtimemap

import androidx.multidex.MultiDexApplication
import com.mapbox.mapboxsdk.Mapbox
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance( this,getString(R.string.access_token))
    }
}