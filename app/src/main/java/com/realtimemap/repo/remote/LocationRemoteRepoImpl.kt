package com.realtimemap.repo.remote

import com.realtimemap.repo.model.UserLocationModel
import com.realtimemap.repo.remote.mapper.RemoteLocationMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LocationRemoteRepoImpl @Inject constructor(
    private val socketClient: SocketClient,
    private val mapper: RemoteLocationMapper
) : LocationRemoteRepo {

    override fun fetchUsersLocations(): Flow<List<UserLocationModel>> = callbackFlow {
        socketClient.listenToUserLocation {
            offer(mapper.mapModelList(it))
        }
       // awaitClose { socketClient.disconnect() }
    }

}