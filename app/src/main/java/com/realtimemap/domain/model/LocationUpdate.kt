package com.realtimemap.domain.model

data class LocationUpdate(
    val id:Int,
    val lat:Double,
    val long:Double
){
    companion object{
        fun empty():LocationUpdate=LocationUpdate(
            id = -1,
            lat = 0.0,
            long = 0.0
        )
    }
}