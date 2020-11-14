package com.realtimemap.presentation.map

import com.realtimemap.presentation.mvi.ViewResult

sealed class MapViewResult:ViewResult {


    sealed class LoadInitialResult : MapViewResult() {
        data class Loaded(val recipes: List<Any>) : LoadInitialResult()
        object Loading : LoadInitialResult()
        object Empty : LoadInitialResult()
        data class Error(val cause: Throwable) : LoadInitialResult()
    }

    sealed class RetryFetchResult : MapViewResult() {
        data class Loaded(val recipes: List<Any>) : RetryFetchResult()
        object Loading : RetryFetchResult()
        object Empty : RetryFetchResult()
        data class Error(val cause: Throwable) : RetryFetchResult()
    }

    sealed class RefreshLocationsResult : MapViewResult() {
        data class Loaded(val recipes: List<Any>) : RefreshLocationsResult()
        object Refreshing : RefreshLocationsResult()
        object Empty : RefreshLocationsResult()
        data class Error(val cause: Throwable) : RefreshLocationsResult()
    }
}