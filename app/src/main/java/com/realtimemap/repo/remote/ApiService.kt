package com.realtimemap.repo.remote

import com.realtimemap.repo.remote.dto.GeoCodingResp
import com.realtimemap.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("geocoding/v5/mapbox.places/{lat},{long}.json?")
    suspend fun fetchAddress(
        @Path("lat") lat: Double,
        @Path("long") long: Double,
        @Query("access_token") token:String =Constants.API_KEY_GEOCODING,
        @Query("types")type:String= "address",
        @Query("limit")limit:Int = 1
    ): GeoCodingResp
}
