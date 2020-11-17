package com.realtimemap.presentation.map

import com.realtimemap.domain.model.UserLocation
import com.realtimemap.presentation.mvi.ViewIntent

sealed class MapViewIntent : ViewIntent {
    object LoadInitialViewIntent : MapViewIntent()
    object GetLocationUpdatesIntent : MapViewIntent()
    object RetryFetchViewIntent : MapViewIntent()
    data class ShowLocationDetailIntent
        (val userLocation: UserLocation) : MapViewIntent()
}