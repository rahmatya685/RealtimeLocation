package com.realtimemap.repo.remote

import com.realtimemap.domain.model.UpdatedLocation
import com.realtimemap.domain.model.UserLocation
import kotlinx.coroutines.flow.Flow

interface LocationRemoteRepo {
    fun fetchUsersLocations(): Flow<List<UserLocation>>
    fun fetchUserLocationUpdates(): Flow<UpdatedLocation>
}