package com.realtimemap.di.module

import com.realtimemap.repo.remote.LocationRemoteRepo
import com.realtimemap.repo.remote.LocationRemoteRepoImpl
import com.realtimemap.repo.remote.SocketClient
import com.realtimemap.repo.remote.SocketClientImpl
import dagger.Binds
import dagger.Module
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

}
