package com.example.data.entity.flight

data class RegularFareEntity(
    val fareClass: String? = null,
    val fares: List<FareEntity>? = null
)