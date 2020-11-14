package com.realtimemap.di.module

import com.realtimemap.repo.remote.SocketParams
import com.realtimemap.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface SocketModule {
    companion object {
        @Provides
        fun provideSocket(): SocketParams {
            return SocketParams(
                ip = Constants.URL,
                port = Constants.PORT,
                authentication = Constants.AUTHENTICATION
            )
        }

    }
}