package com.example.data.entity.flight

data class TripEntity(
    val origin: String? = null,
    val originName: String? = null,
    val destination: String? = null,
    val destinationName: String? = null,
    val dates: List<FlightDateEntity>? = null
)