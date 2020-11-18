package com.realtimemap.repo

import com.realtimemap.domain.model.LocationUpdate
import com.realtimemap.domain.model.UserLocation
import com.realtimemap.domain.model.UserLocationWithAddressModel
import com.realtimemap.repo.remote.DummyData
import com.realtimemap.repo.remote.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.net.SocketTimeoutException

class FakeLocationRepository : LocationRepository {

    companion object {
        const val ERROR_MSG: String = "No network"
    }

    private var flowUserLocations = flowOf(DummyData.userLocations)

    override fun fetchLocations(): Flow<List<UserLocation>> = flowUserLocations

    internal var responseTypeLocationList: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            flowUserLocations = makeResponseLocationList(value)
        }

    private fun makeResponseLocationList(type: ResponseType): Flow<MutableList<UserLocation>> {
        return when (type) {
            ResponseType.DATA -> flowOf(DummyData.userLocations)
            ResponseType.EMPTY -> flowOf(mutableListOf<UserLocation>())
            ResponseType.ERROR -> flow { throw SocketTimeoutException(ERROR_MSG) }
        }
    }


    private val flowUserLocationWithAddressModel = flowOf(DummyData.userLocationWithAddressModel)

    override fun fetchAddress(params: UserLocation?): Flow<UserLocationWithAddressModel> =
        flowUserLocationWithAddressModel

    internal var responseTypeLocationUpdates: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            flowLocationUpdates = makeResponseLocationUpdate(value)
        }

    private fun makeResponseLocationUpdate(type: ResponseType): Flow<LocationUpdate> {
        return when (type) {
            ResponseType.DATA -> flowOf(DummyData.locationUpdate)
            ResponseType.EMPTY -> flowOf(LocationUpdate.empty())
            ResponseType.ERROR -> flow { throw SocketTimeoutException(ERROR_MSG) }
        }
    }

    private var flowLocationUpdates: Flow<LocationUpdate> = flowOf(DummyData.locationUpdate)

    override fun fetchLocationUpdates(): Flow<LocationUpdate> = flowLocationUpdates

}

internal enum class ResponseType {
    DATA,
    EMPTY,
    ERROR
}
