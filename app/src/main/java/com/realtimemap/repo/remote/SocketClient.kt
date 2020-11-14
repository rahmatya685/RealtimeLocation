package com.realtimemap.repo.remote

interface SocketClient {

    fun listenToUpdates(userLocationsUpdate: (String) -> Unit)
    fun listenToUserLocation(userLocations: (List<String>) -> Unit)
    fun disconnect()
}