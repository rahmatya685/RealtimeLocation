package com.realtimemap.di

import com.realtimemap.di.components.AppComponent
import com.realtimemap.di.components.CoreComponent
import com.realtimemap.di.components.DaggerMapComponent
import com.realtimemap.view.MapFragment
import dagger.hilt.android.EntryPointAccessors


internal fun inject(fragment: MapFragment) {
    DaggerMapComponent
        .factory()
        .create(coreComponent(fragment), appComponent(fragment))
        .inject(fragment)
}

private fun coreComponent(fragment: MapFragment): CoreComponent =
    EntryPointAccessors.fromApplication(
        fragment.requireContext().applicationContext,
        CoreComponent::class.java
    )

private fun appComponent(fragment: MapFragment): AppComponent =
    EntryPointAccessors.fromActivity(
        fragment.requireActivity(),
        AppComponent::class.java
    )
