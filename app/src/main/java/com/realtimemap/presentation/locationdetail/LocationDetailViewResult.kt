package com.realtimemap.presentation.locationdetail

import com.realtimemap.domain.model.UserLocationWithAddressModel
import com.realtimemap.presentation.mvi.ViewResult

sealed class LocationDetailViewResult:ViewResult{

    sealed class LoadLocationDetailResult : LocationDetailViewResult() {
        data class Loaded(val location: UserLocationWithAddressModel) : LoadLocationDetailResult()
        object Loading : LoadLocationDetailResult()
        object Empty : LoadLocationDetailResult()
        data class Error(val cause: Throwable) : LoadLocationDetailResult()
    }


    sealed class RetryLoadResult : LocationDetailViewResult() {
        data class Loaded(val location: UserLocationWithAddressModel) : RetryLoadResult()
        object Loading : RetryLoadResult()
        object Empty : RetryLoadResult()
        data class Error(val cause: Throwable) : RetryLoadResult()
    }
}