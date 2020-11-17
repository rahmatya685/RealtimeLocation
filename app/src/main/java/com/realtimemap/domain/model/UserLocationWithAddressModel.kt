package com.realtimemap.domain.model

class UserLocationWithAddressModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val lat: Double,
    val long: Double,
    val address: String
) {
    companion object {
        fun instance(userLocation: UserLocation, address: String): UserLocationWithAddressModel =
            UserLocationWithAddressModel(
                id = userLocation.id,
                name = userLocation.name,
                imageUrl = userLocation.imageUrl,
                lat = userLocation.lat,
                long = userLocation.long,
                address = address
            )
    }
}