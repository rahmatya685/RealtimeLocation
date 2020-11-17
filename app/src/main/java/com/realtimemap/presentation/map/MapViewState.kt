package com.realtimemap.presentation.map

import com.realtimemap.domain.model.LocationUpdate
import com.realtimemap.domain.model.UserLocation
import com.realtimemap.presentation.event.ViewEvent
import com.realtimemap.presentation.mvi.ViewState
import com.realtimemap.repo.model.UserLocationModel

data class MapViewState private constructor(
    val isLoading: Boolean,
    val isDataUnavailable: Boolean,
    val isLocationUpdated:Boolean,
    val updateLocation: LocationUpdate?,
    val error: String?,
    val errorEvent: ViewEvent<String>?,
    val isDataAvailableError:Boolean = false,
    val locations: List<UserLocationModel>,
    val hasLocationWithAddress:Boolean =false,
    val location: ViewEvent<UserLocation>? =null
) : ViewState {

    val loadingState: MapViewState
        get() = this.copy(
            isLoading = true,
            isDataUnavailable = false,
            error = null,
            errorEvent = null
        )

    val emptyState: MapViewState
        get() = this.copy(
            isLoading = false ,
            isDataUnavailable = true,
            error = null,
            errorEvent = null
        )

    fun noDataErrorState(cause: String): MapViewState = this.copy(
        isLoading = false,
        isDataUnavailable = false,
        error = cause,
        errorEvent = null
    )

    fun dataAvailableErrorState(cause: String): MapViewState = this.copy(
        isLoading = false,
        isDataUnavailable = false,
        error = null,
        isDataAvailableError = true,
        errorEvent = ViewEvent(cause)
    )

    fun loadedState(locations: List<UserLocationModel>): MapViewState = this.copy(
        isLoading = false,
        isDataUnavailable = false,
        error = null,
        errorEvent = null,
        locations = locations
    )
    fun updatedState(updateLocation: LocationUpdate): MapViewState = this.copy(
        isLoading = false,
        isDataUnavailable = false,
        error = null,
        errorEvent = null,
        isLocationUpdated = true,
        locations = locations,
        updateLocation = updateLocation
    )

    fun navigateToLocationDetail(userLocation: UserLocation) = this.copy(
        isLoading = false,
        isDataUnavailable = false,
        error = null,
        errorEvent = null,
        isLocationUpdated = false,
        hasLocationWithAddress = true,
        location = ViewEvent(userLocation)
    )



    val isNoDataError: Boolean
        get() = this.error != null


    companion object {
        val init: MapViewState
            get() = MapViewState(
                isLoading = false,
                error = null,
                isDataUnavailable = false,
                errorEvent = null,
                locations = emptyList(),
                isLocationUpdated = false,
                updateLocation =null
            )
    }
}
