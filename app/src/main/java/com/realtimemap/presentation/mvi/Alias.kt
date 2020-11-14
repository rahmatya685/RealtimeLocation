package com.realtimemap.presentation.mvi

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