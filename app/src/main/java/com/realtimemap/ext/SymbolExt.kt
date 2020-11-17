package com.realtimemap.ext

import com.google.gson.Gson
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.realtimemap.domain.model.UserLocation

fun Symbol.toUserLocation(): UserLocation =
    Gson().fromJson(this.data.toString(), UserLocation::class.java)