package com.realtimemap.presentation.map

import com.realtimemap.presentation.event.ViewEvent
import com.realtimemap.presentation.mvi.ViewState
import com.realtimemap.repo.model.UserLocationModel

data class MapViewState private constructor(
    val isLoading: Boolean,
    val isDataUnavailable: Boolean,
    val error: String?,
    val errorEvent: ViewEvent<String>?,
    val locations: List<UserLocationModel>
) : ViewState {

    val loadingState: MapViewState
        get() = this.copy(
            isLoading = true,
            isDataUnavailable = false,
            error = null,
            errorEvent = null
        )

    val refreshingState: MapViewState
        get() = this.copy(
            isLoading = false,
            isDataUnavailable = false,
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
        errorEvent = ViewEvent(cause)
    )

    fun loadedState(locations: List<UserLocationModel>): MapViewState = this.copy(
        isLoading = false,
        isDataUnavailable = false,
        error = null,
        errorEvent = null,
        locations = locations
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
                locations = emptyList()
            )
    }
}
