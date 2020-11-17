package com.realtimemap.di.module

import androidx.lifecycle.ViewModel
import com.realtimemap.view.MapViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoMap

@DisableInstallInCheck
@Module
interface MapViewModelModule {

    @get:[Binds IntoMap ViewModelKey(MapViewModel::class)]
    val MapViewModel.mapViewModel: ViewModel

}
