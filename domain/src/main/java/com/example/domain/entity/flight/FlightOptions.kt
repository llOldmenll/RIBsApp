package com.example.domain.entity.flight

data class FlightOptions(
    val list: List<FlightOption> = listOf(),
    var path: String = ""
)