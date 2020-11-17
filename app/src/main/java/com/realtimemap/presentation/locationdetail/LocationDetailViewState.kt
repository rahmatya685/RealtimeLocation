package com.realtimemap.presentation.locationdetail

import com.realtimemap.domain.model.UserLocationWithAddressModel
import com.realtimemap.presentation.event.ViewEvent
import com.realtimemap.presentation.mvi.ViewState

data class LocationDetailViewState private constructor(
    val isLoading: Boolean,
    val hasError:Boolean,
    val noData:Boolean,
    val error: String?,
    val errorEvent: ViewEvent<String>?,
    val hasLocationWithAddress:Boolean =false,
    val locationWithAddress: UserLocationWithAddressModel? =null
) : ViewState {

    val loadingState: LocationDetailViewState
        get() = this.copy(
            isLoading = true,
            error = null,
            errorEvent = null,
            locationWithAddress = null,
            hasLocationWithAddress = false
        )

    val emptyState: LocationDetailViewState
        get() = this.copy(
            isLoading = false ,
            error = null,
            errorEvent = null,
            locationWithAddress = null,
            hasLocationWithAddress = false,
            noData = true
        )

    fun noDataErrorState(cause: String): LocationDetailViewState = this.copy(
        isLoading = false,
        error = cause,
        errorEvent = null,
        noData = true,
        locationWithAddress = null,
        hasLocationWithAddress = false
    )


    fun loadedState(location:UserLocationWithAddressModel): LocationDetailViewState = this.copy(
        isLoading = false,
        error = null,
        errorEvent = null,
        locationWithAddress = location,
        hasLocationWithAddress = true
    )

    fun dataAvailableErrorState(cause: String): LocationDetailViewState= this.copy(
        isLoading = false,
        error = cause,
        errorEvent = null,
        locationWithAddress = null,
        hasLocationWithAddress = false
    )

    companion object {
        val init: LocationDetailViewState
            get() = LocationDetailViewState(
                isLoading = false,
                error = null,
                errorEvent = null,
                locationWithAddress = null,
                hasLocationWithAddress = false,
                noData = false,
                hasError = false
            )
    }
}