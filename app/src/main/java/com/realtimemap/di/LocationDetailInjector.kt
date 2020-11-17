package com.realtimemap.di

import com.realtimemap.di.components.AppComponent
import com.realtimemap.di.components.CoreComponent
import com.realtimemap.di.components.DaggerLocationDetailComponent
import com.realtimemap.view.LocationDetailFragment
import dagger.hilt.android.EntryPointAccessors


internal fun inject(fragment: LocationDetailFragment) {
    DaggerLocationDetailComponent
        .factory()
        .create(coreComponent(fragment), appComponent(fragment))
        .inject(fragment)
}

private fun coreComponent(fragment: LocationDetailFragment): CoreComponent =
    EntryPointAccessors.fromApplication(
        fragment.requireContext().applicationContext,
        CoreComponent::class.java
    )

private fun appComponent(fragment: LocationDetailFragment): AppComponent =
    EntryPointAccessors.fromActivity(
        fragment.requireActivity(),
        AppComponent::class.java
    )
