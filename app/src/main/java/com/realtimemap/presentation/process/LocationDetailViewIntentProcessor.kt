package com.realtimemap.presentation.process

import com.realtimemap.di.module.FeatureScope
import com.realtimemap.presentation.locationdetail.LocationDetailViewAction
import com.realtimemap.presentation.locationdetail.LocationDetailViewIntent
import com.realtimemap.presentation.mvi.IntentProcessor
import javax.inject.Inject

@FeatureScope
class LocationDetailViewIntentProcessor @Inject constructor(
) :
    IntentProcessor<LocationDetailViewIntent, LocationDetailViewAction> {

    override fun intentToAction(intent: LocationDetailViewIntent): LocationDetailViewAction {
        return when (intent) {
            is LocationDetailViewIntent.LoadInfoIntent -> showLocationDetail(intent)
        }
    }

    private fun showLocationDetail(
        intent: LocationDetailViewIntent.LoadInfoIntent
    ): LocationDetailViewAction.LoadInfoAction {
        return LocationDetailViewAction.LoadInfoAction(
            intent.userLocation
        )
    }
}