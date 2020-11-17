package com.realtimemap.repo.remote

import com.realtimemap.domain.model.LocationUpdate
import com.realtimemap.domain.model.UserLocation
import com.realtimemap.domain.model.UserLocationWithAddressModel
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

    override fun fetchAddress(params: UserLocation?): Flow<UserLocationWithAddressModel> = flow {
        val geojsonResp = locationRemoteRepo.fetchAddress(params!!.lat, params!!.long)
        val address =
            if (geojsonResp.features.isNotEmpty())
                geojsonResp.features.first().place_name
            else
                "No address found"
        emit(
            UserLocationWithAddressModel.instance(
                userLocation = params,
                address = address
            )
        )
    }

}