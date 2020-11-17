package com.realtimemap.presentation.process

import com.realtimemap.di.module.FeatureScope
import com.realtimemap.presentation.locationdetail.LocationDetailViewResult
import com.realtimemap.presentation.locationdetail.LocationDetailViewState
import com.realtimemap.presentation.mvi.ViewStateReducer
import javax.inject.Inject

@FeatureScope
class LocationDetailViewStateReducer @Inject constructor( ) :
    ViewStateReducer<LocationDetailViewState, LocationDetailViewResult> {

    override fun reduce(previous: LocationDetailViewState, result: LocationDetailViewResult): LocationDetailViewState {
        return when (result) {
            is LocationDetailViewResult.RetryLoadResult -> handleRetryFetchResult(
                result,
                previous
            )
            is LocationDetailViewResult.LoadLocationDetailResult-> handleLoadAddressResult(
                result,
                previous
            )
        }
    }

    private fun handleLoadAddressResult(
        result: LocationDetailViewResult.LoadLocationDetailResult,
        previous: LocationDetailViewState
    ): LocationDetailViewState {
        return  when(result){
            is LocationDetailViewResult.LoadLocationDetailResult.Loaded -> previous.loadedState(
                location = result.location
            )
            LocationDetailViewResult.LoadLocationDetailResult.Loading -> previous.loadingState
            LocationDetailViewResult.LoadLocationDetailResult.Empty -> handleEmptyState(previous)
            is LocationDetailViewResult.LoadLocationDetailResult.Error -> handleErrorState(
                previous,
                result.cause.message!!
            )
        }
    }



    private fun handleEmptyState(previous: LocationDetailViewState): LocationDetailViewState {
        return if (previous.locationWithAddress==null) previous.emptyState
        else previous.loadedState(previous.locationWithAddress)
    }

    private fun handleErrorState(previous: LocationDetailViewState, cause: String): LocationDetailViewState {
        return if (previous.locationWithAddress== null) previous.noDataErrorState(cause)
        else previous.dataAvailableErrorState(cause)
    }



    private fun handleRetryFetchResult(
        retryFetchResult: LocationDetailViewResult.RetryLoadResult,
        previous: LocationDetailViewState
    ): LocationDetailViewState {
        return when (retryFetchResult) {
            is LocationDetailViewResult.RetryLoadResult.Loaded -> previous.loadedState(
                retryFetchResult.location
            )
            is LocationDetailViewResult.RetryLoadResult.Error -> handleErrorState(
                previous,
                retryFetchResult.cause.message!!
            )
            LocationDetailViewResult.RetryLoadResult.Loading -> previous.loadingState
            LocationDetailViewResult.RetryLoadResult.Empty -> handleEmptyState(previous)
        }
    }


}
