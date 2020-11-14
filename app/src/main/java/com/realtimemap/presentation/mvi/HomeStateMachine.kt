package com.realtimemap.presentation.mvi

import com.realtimemap.presentation.map.MapViewAction
import com.realtimemap.presentation.map.MapViewIntent
import com.realtimemap.presentation.map.MapViewResult
import com.realtimemap.presentation.map.MapViewState
import javax.inject.Inject

class HomeStateMachine @Inject constructor(
    actionProcessor: MapViewActionProcessor,
    intentProcessor: MapIntentProcessor,
    stateReducer: MapStateReducer
) : StateMachine<MapViewAction, MapViewIntent, MapViewState, MapViewResult>(
    actionProcessor,
    intentProcessor,
    stateReducer,
    MapViewAction.DoNothing,
    MapViewState.init
)