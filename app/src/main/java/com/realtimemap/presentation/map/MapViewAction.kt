package com.realtimemap.presentation.map

import com.realtimemap.presentation.mvi.ViewAction
import com.realtimemap.repo.model.UserLocationModel

sealed class MapViewAction : ViewAction {
    object RetryFetchAction : MapViewAction()
    object LoadLocationsAction : MapViewAction()
    object GetLocationUpdatesAction : MapViewAction()
    object DoNothing : MapViewAction()
    data class ShowLocationDetailAction(val userLocationModel: UserLocationModel) : MapViewAction()
}