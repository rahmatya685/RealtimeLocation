package com.realtimemap.di.components

import com.realtimemap.di.module.FactoryModule
import com.realtimemap.di.module.FeatureScope
import com.realtimemap.di.module.PresentationModule
import com.realtimemap.di.module.ViewModelModule
import com.realtimemap.view.MapFragment
import dagger.Component

@FeatureScope
@Component(
    dependencies = [CoreComponent::class, AppComponent::class],
    modules = [FactoryModule::class, ViewModelModule::class, PresentationModule::class]
)
interface MapComponent {

    fun inject(recipeFragment: MapFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            appComponent: AppComponent
        ): MapComponent
    }
}
