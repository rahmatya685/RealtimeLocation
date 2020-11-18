package com.realtimemap

import com.realtimemap.domain.usecase.FetchLocationUpdates
import com.realtimemap.domain.usecase.FetchLocations
import com.realtimemap.presentation.map.MapViewAction
import com.realtimemap.presentation.map.MapViewResult
import com.realtimemap.presentation.process.MapActionProcessor
import com.realtimemap.repo.FakeLocationRepository
import com.realtimemap.repo.ResponseType
import com.realtimemap.repo.remote.DummyData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take

object FakeMapViewActionProcessor {


    private val testPostExecutionThread: TestPostExecutionThread = TestPostExecutionThread()

    private val locationRepository: FakeLocationRepository = FakeLocationRepository()

    private val fetchLocations = FetchLocations(locationRepository, testPostExecutionThread)

    private val fetchLocationUpdates =
        FetchLocationUpdates(locationRepository, testPostExecutionThread)


    private fun baseLocationUpdateResult(
        type: ResponseType,
        action: MapViewAction
    ): Flow<MapViewResult> {
        locationRepository.responseTypeLocationList = type
        locationRepository.responseTypeLocationUpdates = type
        return MapActionProcessor(fetchLocations, fetchLocationUpdates).actionToResult(action).take(2)
    }


    val loadLocationActionResultData: Flow<MapViewResult>
        get() = baseLocationUpdateResult(ResponseType.DATA, MapViewAction.LoadLocationsAction)
    val loadLocationActionResultEmpty: Flow<MapViewResult>
        get() = baseLocationUpdateResult(ResponseType.EMPTY, MapViewAction.LoadLocationsAction)
    val loadLocationActionResultError: Flow<MapViewResult>
        get() = baseLocationUpdateResult(ResponseType.ERROR, MapViewAction.LoadLocationsAction)




    val retryActionResultData: Flow<MapViewResult>
        get() = baseLocationUpdateResult(ResponseType.DATA, MapViewAction.RetryFetchAction)
    val retryActionResultError: Flow<MapViewResult>
        get() = baseLocationUpdateResult(ResponseType.ERROR, MapViewAction.RetryFetchAction)
    val retryActionResultEmpty: Flow<MapViewResult>
        get() = baseLocationUpdateResult(ResponseType.EMPTY, MapViewAction.RetryFetchAction)




    val locationUpdatesActionResultData: Flow<MapViewResult>
        get() = baseLocationUpdateResult(ResponseType.DATA, MapViewAction.GetLocationUpdatesAction)
    val locationUpdatesActionResultEmpty: Flow<MapViewResult>
        get() = baseLocationUpdateResult(ResponseType.EMPTY, MapViewAction.GetLocationUpdatesAction)
    val locationUpdatesActionResultError: Flow<MapViewResult>
        get() = baseLocationUpdateResult(ResponseType.ERROR, MapViewAction.GetLocationUpdatesAction)



    val locationDetailActionResultData: Flow<MapViewResult>
        get() = baseLocationUpdateResult(ResponseType.DATA, MapViewAction.ShowLocationDetailAction(DummyData.userLocationModel))
    val locationDetailActionResultEmpty: Flow<MapViewResult>
        get() = baseLocationUpdateResult(ResponseType.EMPTY, MapViewAction.ShowLocationDetailAction(DummyData.userLocationModel))
    val locationDetailActionResultError: Flow<MapViewResult>
        get() = baseLocationUpdateResult(ResponseType.ERROR, MapViewAction.ShowLocationDetailAction(DummyData.userLocationModel))


}