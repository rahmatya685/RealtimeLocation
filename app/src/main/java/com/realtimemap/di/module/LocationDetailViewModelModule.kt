package com.realtimemap.di.module

import androidx.lifecycle.ViewModel
import com.realtimemap.view.LocationDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoMap

@DisableInstallInCheck
@Module
interface LocationDetailViewModelModule {


    @get:[Binds IntoMap ViewModelKey(LocationDetailViewModel::class)]
    val LocationDetailViewModel.locationDetailView:ViewModel
}
