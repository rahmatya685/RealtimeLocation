package com.realtimemap.repo.remote

import com.realtimemap.domain.model.LocationUpdate
import com.realtimemap.domain.model.UserLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationRemoteRepo: LocationRemoteRepo
) : LocationRepository {
    override fun fetchLocations(): Flow<List<UserLocation>> =
        locationRemoteRepo.fetchUsersLocations()

    override fun fetchLocationUpdates():
            Flow<LocationUpdate> = locationRemoteRepo.fetchUserLocationUpdates()

    override fun fetchAddress(params: UserLocation?): Flow<String> = flow {
        val address =
            locationRemoteRepo.fetchAddress(params!!.lat, params!!.long).features.first().place_name
        emit(address)
    }

}