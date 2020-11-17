package com.realtimemap.presentation.process

import com.realtimemap.di.module.FeatureScope
import com.realtimemap.domain.model.UserLocation
import com.realtimemap.domain.model.UserLocationWithAddress
import com.realtimemap.domain.usecase.FetchAddressUseCase
import com.realtimemap.presentation.locationdetail.LocationDetailViewAction
import com.realtimemap.presentation.locationdetail.LocationDetailViewResult
import com.realtimemap.presentation.mvi.ActionProcessor
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@FeatureScope
class LocationDetailViewActionProcessor @Inject constructor(
    private val fetchAddressUserCase: FetchAddressUseCase
) : ActionProcessor<LocationDetailViewAction, LocationDetailViewResult> {


    override fun actionToResult(viewAction: LocationDetailViewAction): Flow<LocationDetailViewResult> =flow {
        when (viewAction) {
            is LocationDetailViewAction.LoadInfoAction -> locationWithAddress(viewAction.userLocation)
            LocationDetailViewAction.DoNothing -> emptyFlow()
        }
    }

    private fun locationWithAddress(userLocation: UserLocation): Flow<LocationDetailViewResult> =
        fetchAddressUserCase(userLocation).map { address ->
            if (address != null) {
                LocationDetailViewResult.LoadLocationDetailResult.Loaded(
                    UserLocationWithAddress(
                        id = userLocation.id,
                        name = userLocation.name,
                        long = userLocation.long,
                        lat = userLocation.lat,
                        imageUrl = userLocation.imageUrl,
                        address = address
                    )
                )
            } else {
                LocationDetailViewResult.LoadLocationDetailResult.Empty
            }
        }.onStart {
            emit(LocationDetailViewResult.LoadLocationDetailResult.Loading)
        }.catch { cause: Throwable ->
            emit(LocationDetailViewResult.LoadLocationDetailResult.Error(cause))
        }

}
