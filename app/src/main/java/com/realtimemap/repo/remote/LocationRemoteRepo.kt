package com.realtimemap.repo.remote

import com.realtimemap.repo.model.UserLocationModel
import kotlinx.coroutines.flow.Flow

interface LocationRemoteRepo {
    fun fetchUsersLocations(): Flow<List<UserLocationModel>>
}