package com.realtimemap.di.module

import com.realtimemap.presentation.mvi.MapIntentProcessor
import com.realtimemap.presentation.mvi.MapStateReducer
import com.realtimemap.presentation.mvi.MapViewActionProcessor
import com.realtimemap.presentation.process.MapActionProcessor
import com.realtimemap.presentation.process.MapViewIntentProcessor
import com.realtimemap.presentation.process.MapViewStateReducer
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@DisableInstallInCheck
@Module
interface PresentationModule {

    @get:[Binds FeatureScope]
    val MapActionProcessor.actionProcessor: MapViewActionProcessor

    @get:[Binds FeatureScope]
    val MapViewIntentProcessor.intentProcessor: MapIntentProcessor

    @get:[Binds FeatureScope]
    val MapViewStateReducer.reducer: MapStateReducer
}
