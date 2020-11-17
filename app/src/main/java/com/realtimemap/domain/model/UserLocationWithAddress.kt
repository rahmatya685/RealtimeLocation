package com.realtimemap.domain.model

data class UserLocationWithAddress (
    val id:Int,
    val name:String,
    val imageUrl:String,
    val lat:Double,
    val long:Double,
    val address:String
)