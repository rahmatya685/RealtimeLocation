package com.realtimemap.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realtimemap.presentation.map.HomeStateMachine
import com.realtimemap.presentation.map.MapViewIntent
import com.realtimemap.presentation.map.MapViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val homeStateMachine: HomeStateMachine
) : ViewModel() {

    val viewState: StateFlow<MapViewState> = homeStateMachine.viewState

    init {
        homeStateMachine.processor.launchIn(viewModelScope)
    }

    fun processIntent(intents: Flow<MapViewIntent>) {
        homeStateMachine
            .processIntents(intents)
            .launchIn(viewModelScope)
    }
}
