package com.realtimemap.repo.model

import javax.inject.Inject

class MapModelMapper @Inject constructor(
) : ModelMapper<UserLocationModel, Any> {

    override fun mapToModel(domain: Any): UserLocationModel {
        return UserLocationModel( 0,"","",0.0,0.0 )
    }

    override fun mapToDomain(model: UserLocationModel): Any {
        return Any()
    }
}
