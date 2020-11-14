package com.realtimemap.repo.remote

import com.realtimemap.domain.model.UpdatedLocation
import com.realtimemap.domain.model.UserLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationRemoteRepo: LocationRemoteRepo
) : LocationRepository {
    override fun fetchLocations(): Flow<List<UserLocation>> =
        locationRemoteRepo.fetchUsersLocations()

    override fun fetchLocationUpdates(): Flow<UpdatedLocation> =locationRemoteRepo.fetchUserLocationUpdates()
}