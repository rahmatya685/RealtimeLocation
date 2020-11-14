package com.realtimemap.repo.remote

interface SocketClient {
    fun listenToUserLocation(
        userLocations: (List<String>) -> Unit
    )
    fun listenToUpdates(
        userLocationsUpdate: (List<String>) -> Unit
    )

    fun disconnect()
}