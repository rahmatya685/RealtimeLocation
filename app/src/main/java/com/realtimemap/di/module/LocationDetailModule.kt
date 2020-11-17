package com.realtimemap.di.module

import com.realtimemap.presentation.mvi.LocationDetailActionProcessor
import com.realtimemap.presentation.mvi.LocationDetailIntentProcessor
import com.realtimemap.presentation.mvi.LocationDetailStateReducer
import com.realtimemap.presentation.process.LocationDetailViewActionProcessor
import com.realtimemap.presentation.process.LocationDetailViewIntentProcessor
import com.realtimemap.presentation.process.LocationDetailViewStateReducer
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@DisableInstallInCheck
@Module
interface LocationDetailModule {


    @get:[Binds FeatureScope]
    val LocationDetailViewActionProcessor.locationDetailActionProcessor: LocationDetailActionProcessor

    @get:[Binds FeatureScope]
    val LocationDetailViewIntentProcessor .locationDetailIntentProcessor: LocationDetailIntentProcessor

    @get:[Binds FeatureScope]
    val LocationDetailViewStateReducer.locationDetailReducer: LocationDetailStateReducer

}
