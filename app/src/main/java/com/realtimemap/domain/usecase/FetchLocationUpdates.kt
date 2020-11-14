package com.realtimemap.domain.usecase

import com.realtimemap.domain.model.UpdatedLocation
import com.realtimemap.domain.usecase.base.FlowUseCase
import com.realtimemap.presentation.event.PostExecutionThread
import com.realtimemap.repo.remote.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchLocationUpdates @Inject constructor(
    private val recipeRepository: LocationRepository,
    postExecutionThread: PostExecutionThread
) : FlowUseCase<Unit, UpdatedLocation>(postExecutionThread) {

    override fun execute(params: Unit?): Flow<UpdatedLocation> {
        return recipeRepository.fetchLocationUpdates()
    }
}