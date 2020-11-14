package com.realtimemap.navigation

import androidx.navigation.NavController
import javax.inject.Inject

class NavigationDispatcherImpl @Inject constructor(
    private val navController: NavController
) : NavigationDispatcher {
    override fun goBack() {
        navController.navigateUp()
    }


}