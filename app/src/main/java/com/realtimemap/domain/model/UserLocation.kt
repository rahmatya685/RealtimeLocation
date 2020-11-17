package com.realtimemap.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserLocation(
    val id:Int,
    val name:String,
    val imageUrl:String,
    val lat:Double,
    val long:Double
): Parcelable