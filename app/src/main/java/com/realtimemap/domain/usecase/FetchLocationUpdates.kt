package com.realtimemap.domain.usecase

import com.realtimemap.domain.model.LocationUpdate
import com.realtimemap.domain.usecase.base.FlowUseCase
import com.realtimemap.presentation.event.PostExecutionThread
import com.realtimemap.repo.remote.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchLocationUpdates @Inject constructor(
    private val locationRepository: LocationRepository,
    postExecutionThread: PostExecutionThread
) : FlowUseCase<Unit, LocationUpdate>(postExecutionThread) {

    override fun execute(params: Unit?): Flow<LocationUpdate> {
        return locationRepository.fetchLocationUpdates()
    }
}