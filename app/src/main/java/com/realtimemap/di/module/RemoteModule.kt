package com.realtimemap.di.module

import com.realtimemap.BuildConfig
import com.realtimemap.repo.remote.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RemoteModule {

    @get:[Binds Singleton]
    val LocationRemoteRepoImpl.bindRemote: LocationRemoteRepo


    @get:[Binds Singleton]
    val SocketClientImpl.socket: SocketClient

    companion object {
        @get:[Provides Singleton]
        val provideMoshi: Moshi
            get() = Moshi.Builder()
                .add(KotlinJsonAdapterFactory()).build()

        @[Provides Singleton]
        fun provideApiService(moshi: Moshi): ApiService =
            ApiServiceFactory.makeAPiService(BuildConfig.DEBUG, moshi)
    }

}
