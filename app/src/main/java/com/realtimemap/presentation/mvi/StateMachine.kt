package com.realtimemap.presentation.mvi

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

abstract class StateMachine<A : ViewAction, I : ViewIntent, S : ViewState, out R : ViewResult>(
    private val actionProcessor: ActionProcessor<A, R>,
    private val intentProcessor: IntentProcessor<I, A>,
    private val reducer: ViewStateReducer<S, R>,
    initialAction: A,
    initialState: S
) {

    private val viewStateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)

    private val intentsChannel: ConflatedBroadcastChannel<A> =
        ConflatedBroadcastChannel(initialAction)

    fun processIntents(intents: Flow<I>): Flow<I> = intents.onEach { viewIntents ->
        intentsChannel.offer(intentProcessor.intentToAction(viewIntents))
    }

    val viewState: StateFlow<S>
        get() = viewStateFlow

    val processor: Flow<S> = intentsChannel.asFlow()
        .flatMapMerge { action ->
            actionProcessor.actionToResult(action)
        }.scan(initialState) { previous, result ->
            reducer.reduce(previous, result)
        }.distinctUntilChanged()
        .onEach { recipeViewState ->
            viewStateFlow.value = recipeViewState
        }
}
