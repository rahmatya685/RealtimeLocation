package com.realtimemap.presentation.process

import com.realtimemap.di.module.FeatureScope
import com.realtimemap.presentation.map.MapViewAction
import com.realtimemap.presentation.map.MapViewIntent
import com.realtimemap.presentation.mvi.IntentProcessor
import com.realtimemap.repo.model.MapModelMapper
import javax.inject.Inject


@FeatureScope
class MapViewIntentProcessor @Inject constructor(
    val mapper: MapModelMapper
) :
    IntentProcessor<MapViewIntent, MapViewAction> {

    override fun intentToAction(intent: MapViewIntent): MapViewAction {
        return when (intent) {
            MapViewIntent.LoadInitialViewIntent -> MapViewAction.LoadLocationsAction
            MapViewIntent.RetryFetchViewIntent -> MapViewAction.RetryFetchAction
            MapViewIntent.GetLocationUpdatesIntent -> MapViewAction.GetLocationUpdatesAction
            is MapViewIntent.ShowLocationDetailIntent -> showLocationDetail(intent)
        }
    }

    private fun showLocationDetail(
        intent: MapViewIntent.ShowLocationDetailIntent
    ): MapViewAction.ShowLocationDetailAction {
        return MapViewAction.ShowLocationDetailAction(
            mapper.mapToModel(intent.userLocation)
        )
    }
}