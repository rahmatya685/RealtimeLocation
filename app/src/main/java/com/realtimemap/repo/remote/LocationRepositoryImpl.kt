package com.realtimemap.repo.remote

import com.realtimemap.repo.model.UserLocationModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationRemoteRepo: LocationRemoteRepo
) : LocationRepository {
    override fun fetchLocations(): Flow<List<UserLocationModel>> =
        locationRemoteRepo.fetchUsersLocations()
}