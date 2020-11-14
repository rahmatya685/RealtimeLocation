package com.realtimemap.di.module

import com.realtimemap.repo.remote.LocationRepository
import com.realtimemap.repo.remote.LocationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @get:[Binds Singleton]
    val LocationRepositoryImpl.locationRepositoryImpl: LocationRepository

}
