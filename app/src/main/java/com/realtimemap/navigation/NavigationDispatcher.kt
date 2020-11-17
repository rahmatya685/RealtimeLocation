package com.realtimemap.navigation

import com.realtimemap.domain.model.UserLocation

interface NavigationDispatcher {
    fun goBack()
    fun openLocationInfoFragment(userLocation: UserLocation)
}