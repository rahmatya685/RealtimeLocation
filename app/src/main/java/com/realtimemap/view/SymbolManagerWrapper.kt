package com.realtimemap.view

import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.realtimemap.domain.model.UserLocation
import com.realtimemap.ext.toUserLocation


typealias SymbolClickListener = (UserLocation) -> Unit

class SymbolManagerWrapper(
    val mapView: MapView, val mapboxMap: MapboxMap, val style: Style
) {

    private val symbolManager = SymbolManager(mapView, mapboxMap, style)
    var symbolClickListener: SymbolClickListener? = null

    fun update(s: Symbol): Any = symbolManager.update(s)
    fun create(symbolOptions: SymbolOptions?): Symbol = symbolManager.create(symbolOptions)


    init {
        symbolManager.addClickListener { s ->
            s?.let {
                val userLocation = s.toUserLocation()
                symbolClickListener?.invoke(userLocation)
            }
            true
        }
    }
}

