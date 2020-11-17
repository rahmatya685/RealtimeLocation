package com.realtimemap.repo.remote.mapper

import com.realtimemap.domain.model.LocationUpdate
import javax.inject.Inject

class LocationUpdatesMapper @Inject constructor() :
    RemoteModelMapper<String, LocationUpdate> {
    override fun mapFromModel(model: String): LocationUpdate {
        val attributes = model.trim().split(",")
        return LocationUpdate(
            id = attributes[0].toInt(),
            lat = attributes[1].toDouble(),
            long = attributes[2].toDouble()
        )
    }
}