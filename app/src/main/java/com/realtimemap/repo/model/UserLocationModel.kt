package com.realtimemap.repo.model

data class UserLocationModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val lat: Double,
    val long: Double
)