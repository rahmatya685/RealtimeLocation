package com.realtimemap.repo.remote

import com.realtimemap.domain.model.LocationUpdate
import com.realtimemap.domain.model.UserLocation
import com.realtimemap.domain.model.UserLocationWithAddressModel
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun fetchLocations(): Flow<List<UserLocation>>
    fun fetchLocationUpdates():Flow<LocationUpdate>
    fun fetchAddress(params: UserLocation?): Flow<UserLocationWithAddressModel>
}