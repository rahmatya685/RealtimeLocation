package com.realtimemap.presentation.process

import com.realtimemap.di.module.FeatureScope
import com.realtimemap.presentation.map.MapViewAction
import com.realtimemap.presentation.map.MapViewIntent
import com.realtimemap.presentation.mvi.IntentProcessor
import javax.inject.Inject


@FeatureScope
class MapViewIntentProcessor @Inject constructor() :
    IntentProcessor<MapViewIntent, MapViewAction> {

    override fun intentToAction(intent: MapViewIntent): MapViewAction {
        return when (intent) {
            MapViewIntent.LoadInitialViewIntent -> MapViewAction.LoadInitialAction
            MapViewIntent.RetryFetchViewIntent -> MapViewAction.RetryFetchAction
        }
    }
}