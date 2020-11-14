package com.realtimemap.presentation.process

import com.realtimemap.di.module.FeatureScope
import com.realtimemap.domain.model.UpdatedLocation
import com.realtimemap.domain.model.UserLocation
import com.realtimemap.domain.usecase.FetchLocationUpdates
import com.realtimemap.domain.usecase.FetchLocations
import com.realtimemap.presentation.map.MapViewAction
import com.realtimemap.presentation.map.MapViewResult
import com.realtimemap.presentation.mvi.ActionProcessor
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@FeatureScope
class MapActionProcessor @Inject constructor(
    private val fetchLocationsUseCase: FetchLocations,
    private val fetchLocationUpdatesUseCase: FetchLocationUpdates
) : ActionProcessor<MapViewAction, MapViewResult> {

    private val locations: Flow<List<UserLocation>>
        get() = fetchLocationsUseCase()

    private val locationUpdates: Flow<UpdatedLocation>
        get() = fetchLocationUpdatesUseCase()

    override fun actionToResult(viewAction: MapViewAction): Flow<MapViewResult> {
        return when (viewAction) {
            MapViewAction.RetryFetchAction -> retryFetch
            MapViewAction.LoadLocations -> loadLocations
            MapViewAction.DoNothing -> emptyFlow()
            MapViewAction.GetLocationUpdates ->getUpdates
        }
    }


    private val retryFetch: Flow<MapViewResult>
        get() = locations.map { locations ->
            if (locations.isNotEmpty()) {
                MapViewResult.RetryFetchResult.Loaded(locations)
            } else {
                MapViewResult.RetryFetchResult.Empty
            }
        }.onStart {
            emit(MapViewResult.RetryFetchResult.Loading)
        }.catch { cause: Throwable ->
            emit(MapViewResult.RetryFetchResult.Error(cause))
        }

    private val loadLocations: Flow<MapViewResult>
        get() = locations.map { locations ->
            if (locations.isNotEmpty()) {
                MapViewResult.LoadInitialResult.Loaded(locations = locations)
            } else {
                MapViewResult.LoadInitialResult.Empty
            }
        }.onStart {
            emit(MapViewResult.LoadInitialResult.Loading)
        }.catch { cause: Throwable ->
            emit(MapViewResult.LoadInitialResult.Error(cause))
        }


    private val getUpdates: Flow<MapViewResult>
        get() = locationUpdates.map { location ->
            if (location != null) {
                MapViewResult.GetLocationUpdates.Updated(location)
            } else {
                MapViewResult.GetLocationUpdates.Empty
            }
        }.onStart {
            emit(MapViewResult.GetLocationUpdates.Loading)
        }.catch { cause: Throwable ->
            emit(MapViewResult.GetLocationUpdates.Error(cause))
        }
}
