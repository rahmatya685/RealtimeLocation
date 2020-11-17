package com.realtimemap.domain.usecase

import com.realtimemap.domain.model.UserLocation
import com.realtimemap.domain.model.UserLocationWithAddressModel
import com.realtimemap.domain.usecase.base.FlowUseCase
import com.realtimemap.presentation.event.PostExecutionThread
import com.realtimemap.repo.remote.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAddressUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    postExecutionThread: PostExecutionThread
) : FlowUseCase<UserLocation, UserLocationWithAddressModel>(postExecutionThread) {

    override fun execute(params: UserLocation?): Flow<UserLocationWithAddressModel> {
        return locationRepository.fetchAddress(params)
    }
}