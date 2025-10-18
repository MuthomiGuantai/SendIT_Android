package com.example.sendit.models

data class LocationRequest(
    val pickupAddress: String,
    val latitude: Double,
    val longitude: Double
)