package com.realtimemap.presentation.process

import com.realtimemap.di.module.FeatureScope
import com.realtimemap.domain.model.LocationUpdate
import com.realtimemap.domain.model.UserLocation
import com.realtimemap.domain.usecase.FetchLocationUpdates
import com.realtimemap.domain.usecase.FetchLocations
import com.realtimemap.presentation.map.MapViewAction
import com.realtimemap.presentation.map.MapViewResult
import com.realtimemap.presentation.mvi.ActionProcessor
import com.realtimemap.repo.model.UserLocationModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@FeatureScope
class MapActionProcessor @Inject constructor(
    private val fetchLocationsUseCase: FetchLocations,
    private val fetchLocationUpdatesUseCase: FetchLocationUpdates
) : ActionProcessor<MapViewAction, MapViewResult> {

    private val locations: Flow<List<UserLocation>>
        get() = fetchLocationsUseCase()

    private val locationUpdates: Flow<LocationUpdate>
        get() = fetchLocationUpdatesUseCase()


    override fun actionToResult(viewAction: MapViewAction): Flow<MapViewResult> {
        return when (viewAction) {
            MapViewAction.RetryFetchAction -> retryFetch
            MapViewAction.LoadLocationsAction -> loadLocations
            MapViewAction.DoNothing -> emptyFlow()
            MapViewAction.GetLocationUpdatesAction -> getUpdates
            is MapViewAction.ShowLocationDetailAction -> showLocationDetail(viewAction.userLocationModel)
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
      listOf<UserLocation>()  }

    private val loadLocations: Flow<MapViewResult>
        get() = locations.map { locations ->
            if (locations.isNotEmpty()) {
                MapViewResult.LoadLocationsResult.Loaded(locations = locations)
            } else {
                MapViewResult.LoadLocationsResult.Empty
            }
        }.onStart {
            emit(MapViewResult.LoadLocationsResult.Loading)
        }.catch { cause: Throwable ->
            emit(MapViewResult.LoadLocationsResult.Error(cause))
        }


    private val getUpdates: Flow<MapViewResult>
        get() = locationUpdates.map { location ->
            if (location.id == -1) {
                MapViewResult.GetLocationUpdates.Empty
            } else {
                MapViewResult.GetLocationUpdates.Updated(location)
            }
        }.onStart {
            emit(MapViewResult.GetLocationUpdates.Loading)
        }.catch { cause: Throwable ->
            emit(MapViewResult.GetLocationUpdates.Error(cause))
        }

    private fun showLocationDetail(userLocationModel: UserLocationModel): Flow<MapViewResult> =
        flowOf(MapViewResult.ShowLocationDetail(userLocationModel))
}
