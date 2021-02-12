package com.example.data.entity.flight

data class FlightsAvailabilityEntity(
    val currency: String? = null,
    val trips: List<TripEntity>? = null
)