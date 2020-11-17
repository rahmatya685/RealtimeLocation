package com.realtimemap.presentation.map

import com.realtimemap.presentation.mvi.MapIntentProcessor
import com.realtimemap.presentation.mvi.MapStateReducer
import com.realtimemap.presentation.mvi.MapViewActionProcessor
import com.realtimemap.presentation.mvi.StateMachine
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