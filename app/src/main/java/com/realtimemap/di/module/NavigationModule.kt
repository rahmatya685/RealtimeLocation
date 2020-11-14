package com.realtimemap.di.module

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.realtimemap.R
import com.realtimemap.navigation.NavigationDispatcher
import com.realtimemap.navigation.NavigationDispatcherImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
interface NavigationModule {

    @get:Binds
    val NavigationDispatcherImpl.navigationDispatcher: NavigationDispatcher

    companion object {
        @Provides
        fun provideNavController(activity: Activity): NavController {
            val frg =
                (activity as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.mainNavHostFragment)
            return frg!!.findNavController()
        }

    }
}