package com.realtimemap.presentation.map

import com.realtimemap.domain.model.LocationUpdate
import com.realtimemap.domain.model.UserLocation
import com.realtimemap.presentation.mvi.ViewResult
import com.realtimemap.repo.model.UserLocationModel

sealed class MapViewResult : ViewResult {

    sealed class GetLocationUpdates : MapViewResult() {
        data class Updated(val updatedLocation: LocationUpdate) : GetLocationUpdates()
        object Loading : GetLocationUpdates()
        object Empty : GetLocationUpdates()
        data class Error(val cause: Throwable) : GetLocationUpdates()
    }

    sealed class LoadLocationsResult : MapViewResult() {
        data class Loaded(val locations: List<UserLocation>) : LoadLocationsResult()
        object Loading : LoadLocationsResult()
        object Empty : LoadLocationsResult()
        data class Error(val cause: Throwable) : LoadLocationsResult()
    }
    data class ShowLocationDetail(val location:UserLocationModel) : MapViewResult()


    sealed class RetryFetchResult : MapViewResult() {
        data class Loaded(val locations: List<UserLocation>) : RetryFetchResult()
        object Loading : RetryFetchResult()
        object Empty : RetryFetchResult()
        data class Error(val cause: Throwable) : RetryFetchResult()
    }

}