package com.realtimemap.di.components

import com.realtimemap.presentation.event.PostExecutionThread
import com.realtimemap.repo.remote.LocationRepository
import com.realtimemap.view.ImageLoader
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CoreComponent {
    val imageLoader: ImageLoader
    val recipeRepository: LocationRepository
    val postExecutionThread: PostExecutionThread
}
