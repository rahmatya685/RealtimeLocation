package com.realtimemap.di.components

import com.realtimemap.di.module.FactoryModule
import com.realtimemap.di.module.FeatureScope
import com.realtimemap.di.module.LocationDetailModule
import com.realtimemap.di.module.LocationDetailViewModelModule
import com.realtimemap.view.LocationDetailFragment
import dagger.Component


@FeatureScope
@Component(
    dependencies = [CoreComponent::class, AppComponent::class],
    modules = [FactoryModule::class, LocationDetailViewModelModule::class, LocationDetailModule::class]
)
interface LocationDetailComponent {

    fun inject(frg: LocationDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            appComponent: AppComponent
        ): LocationDetailComponent
    }
}