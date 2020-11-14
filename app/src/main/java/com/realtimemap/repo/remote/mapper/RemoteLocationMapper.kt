package com.realtimemap.repo.remote.mapper

import com.realtimemap.domain.model.UserLocation
import javax.inject.Inject

class RemoteLocationMapper @Inject constructor() :
    RemoteModelMapper<String, UserLocation> {

    override fun mapFromModel(model: String): UserLocation {
        val attributes = model.trim().split(",")
        return UserLocation(
            id = attributes[0].toInt(),
            name = attributes[1],
            imageUrl = attributes[2],
            lat = attributes[3].toDouble(),
            long = attributes[4].toDouble()
        )
    }
}