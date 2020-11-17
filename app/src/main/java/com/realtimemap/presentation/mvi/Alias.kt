package com.realtimemap.presentation.mvi

import com.realtimemap.presentation.locationdetail.LocationDetailViewAction
import com.realtimemap.presentation.locationdetail.LocationDetailViewIntent
import com.realtimemap.presentation.locationdetail.LocationDetailViewResult
import com.realtimemap.presentation.locationdetail.LocationDetailViewState
import com.realtimemap.presentation.map.MapViewAction
import com.realtimemap.presentation.map.MapViewIntent
import com.realtimemap.presentation.map.MapViewResult
import com.realtimemap.presentation.map.MapViewState

typealias MapViewActionProcessor =
        @JvmSuppressWildcards ActionProcessor<MapViewAction, MapViewResult>

typealias MapStateReducer =
        @JvmSuppressWildcards ViewStateReducer<MapViewState, MapViewResult>

typealias MapIntentProcessor =
        @JvmSuppressWildcards IntentProcessor<MapViewIntent, MapViewAction>





typealias LocationDetailActionProcessor =
        @JvmSuppressWildcards ActionProcessor<LocationDetailViewAction, LocationDetailViewResult>

typealias LocationDetailStateReducer =
        @JvmSuppressWildcards ViewStateReducer<LocationDetailViewState, LocationDetailViewResult>

typealias LocationDetailIntentProcessor =
        @JvmSuppressWildcards IntentProcessor<LocationDetailViewIntent, LocationDetailViewAction>