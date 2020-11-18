package com.realtimemap.presentation.process

import com.google.common.truth.Truth
import com.realtimemap.presentation.map.MapViewAction
import com.realtimemap.presentation.map.MapViewIntent
import com.realtimemap.repo.model.MapModelMapper
import com.realtimemap.repo.remote.DummyData
import org.junit.Test

class MapViewIntentProcessorTest {

    val mapViewIntentProcessor = MapViewIntentProcessor(MapModelMapper())

    @Test
    fun `check if LoadInitialViewIntent is converted to the LoadLocationsAction`() {
        val action = mapViewIntentProcessor.intentToAction(MapViewIntent.LoadInitialViewIntent)
        Truth.assertThat(action).isEqualTo(MapViewAction.LoadLocationsAction)
    }


    @Test
    fun `check if GetLocationUpdatesIntent is converted to GetLocationUpdatesAction`() {
        val action = mapViewIntentProcessor.intentToAction(MapViewIntent.GetLocationUpdatesIntent)
        Truth.assertThat(action).isEqualTo(MapViewAction.GetLocationUpdatesAction)
    }


    @Test
    fun `check if RetryFetchAction is converted to RetryFetchAction`() {
        val action = mapViewIntentProcessor.intentToAction(MapViewIntent.RetryFetchViewIntent)
        Truth.assertThat(action).isEqualTo(MapViewAction.RetryFetchAction)
    }


    @Test
    fun `check if ShowLocationDetailIntent is converted to RetryFetchAction`() {
        val intent = MapViewIntent.ShowLocationDetailIntent(DummyData.userLocation)
        val action = mapViewIntentProcessor.intentToAction(intent)
        Truth.assertThat(action).isInstanceOf(MapViewAction.ShowLocationDetailAction::class.java)
        val showLocationDetailAction = action as MapViewAction.ShowLocationDetailAction
        Truth.assertThat(showLocationDetailAction.userLocationModel)
            .isEqualTo(DummyData.userLocationModel)
    }
}