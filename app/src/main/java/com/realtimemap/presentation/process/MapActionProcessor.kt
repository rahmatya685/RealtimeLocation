package com.realtimemap.presentation.process

import com.realtimemap.di.module.FeatureScope
import com.realtimemap.domain.usecase.FetchLocations
import com.realtimemap.presentation.map.MapViewAction
import com.realtimemap.presentation.map.MapViewResult
import com.realtimemap.presentation.mvi.ActionProcessor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


@FeatureScope
class MapActionProcessor @Inject constructor(
    private val fetchLocationsUseCase: FetchLocations
) : ActionProcessor<MapViewAction, MapViewResult> {

    private val locations: Flow<List<Any>>
        get() = fetchLocationsUseCase()

    override fun actionToResult(viewAction: MapViewAction): Flow<MapViewResult> {
        return when (viewAction) {
            MapViewAction.LoadInitialAction -> loadLocations
            MapViewAction.RetryFetchAction -> retryFetch
        }
    }

    private val retryFetch: Flow<MapViewResult>
        get() = locations.map { recipes ->
            if (recipes.isNotEmpty()) {
                MapViewResult.RetryFetchResult.Loaded(recipes)
            } else {
                MapViewResult.RetryFetchResult.Empty
            }
        }.onStart {
            emit(MapViewResult.RetryFetchResult.Loading)
        }.catch { cause: Throwable ->
            emit(MapViewResult.RetryFetchResult.Error(cause))
        }

    private val loadLocations: Flow<MapViewResult>
        get() = locations.map { recipes ->
            if (recipes.isNotEmpty()) {
                MapViewResult.LoadInitialResult.Loaded(recipes = recipes)
            } else {
                MapViewResult.LoadInitialResult.Empty
            }
        }.onStart {
            emit(MapViewResult.LoadInitialResult.Loading)
        }.catch { cause: Throwable ->
            emit(MapViewResult.LoadInitialResult.Error(cause))
        }
}
