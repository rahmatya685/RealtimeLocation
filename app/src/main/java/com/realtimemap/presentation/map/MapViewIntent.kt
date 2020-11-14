package com.realtimemap.presentation.map

import com.realtimemap.presentation.mvi.ViewIntent

sealed class MapViewIntent : ViewIntent {
    object LoadInitialViewIntent : MapViewIntent()
    object GetLocationUpdates:MapViewIntent()
    object RetryFetchViewIntent : MapViewIntent()
}