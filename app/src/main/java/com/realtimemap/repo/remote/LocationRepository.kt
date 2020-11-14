package com.realtimemap.repo.remote

import com.realtimemap.repo.model.UserLocationModel
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun fetchLocations(): Flow<List<UserLocationModel>>
}