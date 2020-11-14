package com.realtimemap.repo.model

import com.realtimemap.domain.model.UserLocation
import javax.inject.Inject

class MapModelMapper @Inject constructor(
) : ModelMapper<UserLocationModel, UserLocation> {

    override fun mapToModel(domain: UserLocation): UserLocationModel {
        return UserLocationModel(
            id = domain.id,
            name = domain.name,
            imageUrl = domain.imageUrl,
            lat = domain.lat,
            long = domain.long
        )
    }

    override fun mapToDomain(model: UserLocationModel): UserLocation {
        return UserLocation(
            id = model.id,
            name = model.name,
            imageUrl = model.imageUrl,
            lat = model.lat,
            long = model.long
        )
    }
}
