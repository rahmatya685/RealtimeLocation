package com.realtimemap.di.components

import com.realtimemap.di.module.FactoryModule
import com.realtimemap.di.module.FeatureScope
import com.realtimemap.di.module.MapPresentationModule
import com.realtimemap.di.module.MapViewModelModule
import com.realtimemap.view.MapFragment
import dagger.Component

@FeatureScope
@Component(
    dependencies = [CoreComponent::class, AppComponent::class],
    modules = [FactoryModule::class, MapViewModelModule::class, MapPresentationModule::class]
)
interface MapComponent {

    fun inject(frg: MapFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            appComponent: AppComponent
        ): MapComponent
    }
}
