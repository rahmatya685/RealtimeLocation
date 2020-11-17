package com.realtimemap.repo.remote

import com.realtimemap.repo.remote.dto.GeoCodingResp
import com.realtimemap.domain.model.LocationUpdate
import com.realtimemap.domain.model.UserLocation
import com.realtimemap.repo.remote.mapper.LocationUpdatesMapper
import com.realtimemap.repo.remote.mapper.RemoteLocationMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LocationRemoteRepoImpl @Inject constructor(
    private val socketClient: SocketClient,
    private val remoteLocationMapper: RemoteLocationMapper,
    private val locationUpdatesMapper: LocationUpdatesMapper,
    private val apiService: ApiService
) : LocationRemoteRepo {

    override fun fetchUsersLocations(): Flow<List<UserLocation>> = callbackFlow {
        socketClient.listenToUserLocation {
            offer(remoteLocationMapper.mapModelList(it))
        }
        // awaitClose { socketClient.disconnect() }
    }

    override fun fetchUserLocationUpdates(): Flow<LocationUpdate> = callbackFlow {
        socketClient.listenToUpdates {
            offer(locationUpdatesMapper.mapFromModel(it))
        }
        // awaitClose { socketClient.disconnect() }
    }

    override suspend fun fetchAddress(lat: Double, long: Double): GeoCodingResp =
        apiService.fetchAddress(lat, long)

}