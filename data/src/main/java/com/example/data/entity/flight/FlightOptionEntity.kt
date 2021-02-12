package com.example.data.entity.flight

data class FlightOptionEntity(
    val infantsLeft: Int? = null,
    val regularFare: RegularFareEntity? = null,
    val flightNumber: String? = null,
    val time: List<String>? = null,
    val duration: String? = null
)