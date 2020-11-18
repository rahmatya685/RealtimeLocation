package com.realtimemap.repo.remote

import com.realtimemap.domain.model.LocationUpdate
import com.realtimemap.domain.model.UserLocation
import com.realtimemap.domain.model.UserLocationWithAddressModel
import com.realtimemap.repo.model.UserLocationModel
import kotlin.random.Random

object DummyData {
    const val USER_LIST =
        "USERLIST 101,Jānis Bērziņš,https://i4.ifrype.com/profile/000/324/v1559116100/ngm_324.jpg,56.9495677035,24.1064071655;102,Pēteris Zariņš,https://i7.ifrype.com/profile/666/047/v1572553757/ngm_4666047.jpg,56.9503693176,24.1084241867;"

    const val USER_LOCATION_STRING =
        " 101,Jānis Bērziņš,https://i4.ifrype.com/profile/000/324/v1559116100/ngm_324.jpg,56.9495677035 , 24.1064071655"

    const val UPDATE = "UPDATE 101,56.9495677035,24.1064071655"


    val userLocationModel = UserLocationModel(
        id = 100,
        name = "Alireza",
        imageUrl = "awesome url",
        long = 56.323,
        lat = 65.65
    )
    val userLocation = UserLocation(
        id = 100,
        name = "Alireza",
        imageUrl = "awesome url",
        long = 56.323,
        lat = 65.65
    )

    val userLocations = mutableListOf<UserLocation>(
        userLocation,
        userLocation.copy(
            id = 105,
            name = "Interstellar",
            imageUrl = "Another awesome link",
            lat = 65.659,
            long = 96.65
        )
    )


    val userLocationModels = mutableListOf<UserLocationModel>(
        UserLocationModel(
            id = 105,
            name = "Interstellar",
            imageUrl = "Another awesome link",
            lat = 65.659,
            long = 96.65
        ),
        UserLocationModel(
            id = 100,
            name = "Alireza",
            imageUrl = "awesome url",
            long = 56.323,
            lat = 65.65
        )
    )

    val userLocationWithAddressModel = UserLocationWithAddressModel(
        id = 105,
        name = "Interstellar",
        imageUrl = "Another awesome link",
        lat = 65.659,
        long = 96.65,
        address = "Zanjan"
    )

    val rand = Random(0)
    val locationUpdate = LocationUpdate(
        id = rand.nextInt(),
        lat = rand.nextDouble(),
        long = rand.nextDouble()
    )
    fun getLocationUpdates(): List<LocationUpdate> {
        val rand = Random(0)
        val mutableList = mutableListOf<LocationUpdate>()
        for (i in 0..15)
            mutableList.add(
                LocationUpdate(
                    id = rand.nextInt(),
                    lat = rand.nextDouble(),
                    long = rand.nextDouble()
                )
            )
        return mutableList
    }
}