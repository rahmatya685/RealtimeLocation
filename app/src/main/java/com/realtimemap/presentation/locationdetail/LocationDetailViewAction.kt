package com.realtimemap.presentation.locationdetail

import com.realtimemap.domain.model.UserLocation
import com.realtimemap.presentation.mvi.ViewAction

sealed class LocationDetailViewAction: ViewAction {
    data class LoadInfoAction(val userLocation: UserLocation):LocationDetailViewAction()
    object DoNothing:LocationDetailViewAction()
}