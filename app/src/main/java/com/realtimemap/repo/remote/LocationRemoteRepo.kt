package com.realtimemap.repo.remote

import com.realtimemap.repo.remote.dto.GeoCodingResp
import com.realtimemap.domain.model.LocationUpdate
import com.realtimemap.domain.model.UserLocation
import kotlinx.coroutines.flow.Flow

interface LocationRemoteRepo {
    fun fetchUsersLocations(): Flow<List<UserLocation>>
    fun fetchUserLocationUpdates(): Flow<LocationUpdate>
    suspend fun fetchAddress(lat:Double,long:Double): GeoCodingResp
}