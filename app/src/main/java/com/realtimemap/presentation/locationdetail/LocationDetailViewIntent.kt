package com.realtimemap.presentation.locationdetail

import com.realtimemap.domain.model.UserLocation
import com.realtimemap.presentation.mvi.ViewIntent

sealed class LocationDetailViewIntent:ViewIntent {
    data class LoadInfoIntent(val userLocation: UserLocation) : LocationDetailViewIntent()
}