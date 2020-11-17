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
interface MapPresentationModule {

    @get:[Binds FeatureScope]
    val MapActionProcessor.mapActionProcessor: MapViewActionProcessor

    @get:[Binds FeatureScope]
    val MapViewIntentProcessor.mapIntentProcessor: MapIntentProcessor

    @get:[Binds FeatureScope]
    val MapViewStateReducer.mapReducer: MapStateReducer


}
