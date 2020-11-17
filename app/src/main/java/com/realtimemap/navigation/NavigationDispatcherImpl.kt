package com.realtimemap.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.realtimemap.R
import com.realtimemap.domain.model.UserLocation
import javax.inject.Inject

class NavigationDispatcherImpl @Inject constructor(
    private val navController: NavController
) : NavigationDispatcher {
    override fun goBack() {
        navController.navigateUp()
    }

    override fun openLocationInfoFragment(userLocation: UserLocation) {
        navController.navigate(
            R.id.locationInfoFragment,
            bundleOf(LOCATION_KEY to userLocation)
        )
    }


    companion object {
        const val LOCATION_KEY: String = "LOCATION_KEY"
    }

}