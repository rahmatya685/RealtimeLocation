package com.realtimemap.repo.remote.mapper

import com.realtimemap.repo.model.UserLocationModel
import javax.inject.Inject

class RemoteLocationMapper @Inject constructor() :
    RemoteModelMapper<String, UserLocationModel> {

    override fun mapFromModel(model: String): UserLocationModel {
        val attributes = model.trim().split(",")
        return UserLocationModel(
            id = attributes[0].toInt(),
            name = attributes[1],
            imageUrl = attributes[2],
            lat = attributes[3].toDouble(),
            long = attributes[4].toDouble()
        )
    }
}