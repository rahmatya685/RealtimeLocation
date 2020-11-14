package com.realtimemap.presentation.map

import com.realtimemap.presentation.mvi.ViewAction

sealed  class MapViewAction :ViewAction{
    object LoadInitialAction : MapViewAction()
    object RetryFetchAction : MapViewAction()
}