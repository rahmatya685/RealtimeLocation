package com.realtimemap.presentation.process

import com.realtimemap.di.module.FeatureScope
import com.realtimemap.presentation.map.MapViewResult
import com.realtimemap.presentation.map.MapViewState
import com.realtimemap.presentation.mvi.ViewStateReducer
import com.realtimemap.repo.model.MapModelMapper
import javax.inject.Inject

@FeatureScope
class MapViewStateReducer @Inject constructor(private val mapModelMapper: MapModelMapper) :
    ViewStateReducer<MapViewState, MapViewResult> {

    override fun reduce(previous: MapViewState, result: MapViewResult): MapViewState {
        return when (result) {
            is MapViewResult.LoadInitialResult -> handleLoadInitialResult(
                mapModelMapper,
                result,
                previous
            )
            is MapViewResult.RetryFetchResult -> handleRetryFetchResult(
                mapModelMapper,
                result,
                previous
            )
            is MapViewResult.GetLocationUpdates->handleGetLocationUpdatesResult(
                result,
                previous
            )
        }
    }

    private fun handleGetLocationUpdatesResult(
        result: MapViewResult.GetLocationUpdates,
        previous: MapViewState
    ): MapViewState {
         return  when(result){
             is MapViewResult.GetLocationUpdates.Updated -> previous.updatedState(
                 updateLocation = result.updatedLocation
             )
             MapViewResult.GetLocationUpdates.Loading -> previous.loadingState
             MapViewResult.GetLocationUpdates.Empty -> handleEmptyState(previous)
             is MapViewResult.GetLocationUpdates.Error -> handleErrorState(
                 previous,
                 result.cause.message!!
             )
         }
    }

    private fun handleEmptyState(previous: MapViewState): MapViewState {
        return if (previous.locations.isEmpty()) previous.emptyState
        else previous.loadedState(previous.locations)
    }

    private fun handleErrorState(previous: MapViewState, cause: String): MapViewState {
        return if (previous.locations.isEmpty()) previous.noDataErrorState(cause)
        else previous.dataAvailableErrorState(cause)
    }

    private fun handleLoadInitialResult(
        mapper: MapModelMapper,
        loadInitialResult: MapViewResult.LoadInitialResult,
        previous: MapViewState
    ): MapViewState {
        return when (loadInitialResult) {
            is MapViewResult.LoadInitialResult.Loaded -> previous.loadedState(
                mapper.mapToModelList(
                    loadInitialResult.locations
                )
            )
            is MapViewResult.LoadInitialResult.Error -> handleErrorState(
                previous,
                loadInitialResult.cause.message!!
            )
            MapViewResult.LoadInitialResult.Empty -> handleEmptyState(previous)
            MapViewResult.LoadInitialResult.Loading -> previous.loadingState

        }
    }

    private fun handleRetryFetchResult(
        mapper: MapModelMapper,
        retryFetchResult: MapViewResult.RetryFetchResult,
        previous: MapViewState
    ): MapViewState {
        return when (retryFetchResult) {
            is MapViewResult.RetryFetchResult.Loaded -> previous.loadedState(
                mapper.mapToModelList(
                    retryFetchResult.locations
                )
            )
            is MapViewResult.RetryFetchResult.Error -> handleErrorState(
                previous,
                retryFetchResult.cause.message!!
            )
            MapViewResult.RetryFetchResult.Loading -> previous.loadingState
            MapViewResult.RetryFetchResult.Empty -> handleEmptyState(previous)
        }
    }


}
