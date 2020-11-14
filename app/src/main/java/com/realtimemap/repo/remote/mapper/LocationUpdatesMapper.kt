package com.realtimemap.repo.remote.mapper

import com.realtimemap.domain.model.UpdatedLocation
import javax.inject.Inject

class LocationUpdatesMapper @Inject constructor() :
    RemoteModelMapper<String, UpdatedLocation> {
    override fun mapFromModel(model: String): UpdatedLocation {
        val attributes = model.trim().split(",")
        return UpdatedLocation(
            id = attributes[0].toInt(),
            lat = attributes[1].toDouble(),
            long = attributes[2].toDouble()
        )
    }
}