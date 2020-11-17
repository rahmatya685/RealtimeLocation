package com.realtimemap.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realtimemap.presentation.locationdetail.LocationDetailStateMachine
import com.realtimemap.presentation.locationdetail.LocationDetailViewIntent
import com.realtimemap.presentation.locationdetail.LocationDetailViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

class LocationDetailViewModel  @Inject constructor(
    private val locationDetailStateMachine: LocationDetailStateMachine
) : ViewModel() {

    val viewState: StateFlow<LocationDetailViewState> = locationDetailStateMachine.viewState

    init {
        locationDetailStateMachine.processor.launchIn(viewModelScope)
    }

    fun processIntent(intents: Flow<LocationDetailViewIntent>) {
        locationDetailStateMachine
            .processIntents(intents)
            .launchIn(viewModelScope)
    }
}
