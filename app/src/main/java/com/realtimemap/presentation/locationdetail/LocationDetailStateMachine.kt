package com.realtimemap.presentation.locationdetail

import com.realtimemap.presentation.mvi.LocationDetailActionProcessor
import com.realtimemap.presentation.mvi.LocationDetailIntentProcessor
import com.realtimemap.presentation.mvi.LocationDetailStateReducer
import com.realtimemap.presentation.mvi.StateMachine
import javax.inject.Inject

class LocationDetailStateMachine @Inject constructor(
    actionProcessor: LocationDetailActionProcessor,
    intentProcessor: LocationDetailIntentProcessor,
    stateReducer: LocationDetailStateReducer
) : StateMachine<LocationDetailViewAction, LocationDetailViewIntent, LocationDetailViewState, LocationDetailViewResult>(
    actionProcessor,
    intentProcessor,
    stateReducer,
    LocationDetailViewAction.DoNothing,
    LocationDetailViewState.init
)