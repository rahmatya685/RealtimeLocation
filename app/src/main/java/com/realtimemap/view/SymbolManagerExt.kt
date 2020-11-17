package com.realtimemap.view

import com.realtimemap.domain.model.UserLocation
import com.realtimemap.ext.safeOffer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce


@FlowPreview
@ExperimentalCoroutinesApi
val SymbolManagerWrapper.symbolClicks: Flow<UserLocation>
    get() = callbackFlow<UserLocation> {
        val listener: SymbolClickListener = { userLocation: UserLocation ->
            safeOffer(userLocation)
            Unit
        }
        symbolClickListener = listener
        awaitClose { symbolClickListener = null }
    }.conflate().debounce(200)
