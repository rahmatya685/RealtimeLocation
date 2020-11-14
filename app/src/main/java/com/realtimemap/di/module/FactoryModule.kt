package com.realtimemap.di.module

import androidx.lifecycle.ViewModelProvider
import com.realtimemap.view.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@[Module DisableInstallInCheck]
interface FactoryModule {

    @get:Binds
    val ViewModelFactory.viewModelFactory: ViewModelProvider.Factory
}
