package com.realtimemap.presentation.process

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.realtimemap.FakeMapViewActionProcessor
import com.realtimemap.presentation.map.MapViewResult
import com.realtimemap.presentation.map.MapViewState
import com.realtimemap.repo.model.MapModelMapper
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class MapViewStateReducerTest {

    private val mapModelMapper = MapModelMapper()

    private val mapViewStateReducer = MapViewStateReducer(mapModelMapper)


    @Test
    fun `check when LoadLocationAction with data returned then loadingState is emited`() {
        runBlockingTest {
            val initialState: MapViewState = MapViewState.init
            val result: MapViewResult =
                FakeMapViewActionProcessor.loadLocationActionResultData.toList().first()
            val viewState: MapViewState =
                mapViewStateReducer.reduce(initialState, result)
            assertThat(result).isInstanceOf(MapViewResult.LoadLocationsResult.Loading::class.java)
            Truth.assertThat(viewState).isEqualTo(initialState.loadingState)
        }
    }

    @Test
    fun `check when RetryLoadAction with data returned then loadingState is emited`() {
        runBlockingTest {
            val initialState: MapViewState = MapViewState.init
            val result: MapViewResult =
                FakeMapViewActionProcessor.retryActionResultData.toList().first()
            val viewState: MapViewState =
                mapViewStateReducer.reduce(initialState, result)
            assertThat(result).isInstanceOf(MapViewResult.RetryFetchResult.Loading::class.java)
            Truth.assertThat(viewState).isEqualTo(initialState.loadingState)
        }
    }

    @Test
    fun `check when LoadUpdates with data returned then loadingState is emited`() {
        runBlockingTest {
            val initialState: MapViewState = MapViewState.init
            val result: MapViewResult =
                FakeMapViewActionProcessor.locationUpdatesActionResultData.toList().first()
            val viewState: MapViewState =
                mapViewStateReducer.reduce(initialState, result)
            assertThat(result).isInstanceOf(MapViewResult.GetLocationUpdates.Loading::class.java)
            Truth.assertThat(viewState).isEqualTo(initialState.loadingState)
        }
    }


    @Test
    fun `check when ShowLocationDetail with data returned then ShowLocationDetail is emited`() {
        runBlockingTest {
            val initialState: MapViewState = MapViewState.init
            val result: MapViewResult =
                FakeMapViewActionProcessor.locationDetailActionResultData.toList().first()
            val viewState: MapViewState =
                mapViewStateReducer.reduce(initialState, result)
            assertThat(result).isInstanceOf(MapViewResult.ShowLocationDetail::class.java)
        }
    }


}