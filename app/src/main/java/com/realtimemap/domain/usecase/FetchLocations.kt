package com.realtimemap.domain.usecase

import com.realtimemap.domain.usecase.base.FlowUseCase
import com.realtimemap.presentation.event.PostExecutionThread
import com.realtimemap.repo.model.UserLocationModel
import com.realtimemap.repo.remote.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchLocations @Inject constructor(
    private val recipeRepository: LocationRepository,
    postExecutionThread: PostExecutionThread
) : FlowUseCase<Unit, List<Any>>(postExecutionThread) {

    override fun execute(params: Unit?): Flow<List<UserLocationModel>> {
        return recipeRepository.fetchLocations()
    }
}