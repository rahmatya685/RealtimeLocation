package com.realtimemap.presentation.map

import com.realtimemap.presentation.mvi.ViewAction

sealed  class MapViewAction :ViewAction{
    object RetryFetchAction : MapViewAction()
    object LoadLocations:MapViewAction()
    object GetLocationUpdates:MapViewAction()
    object DoNothing:MapViewAction()
}