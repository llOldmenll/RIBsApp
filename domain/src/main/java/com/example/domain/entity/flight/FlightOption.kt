package com.example.domain.entity.flight

data class FlightOption(
    val flightNumber: String,
    val dateOut: String,
    val dateIn: String,
    val duration: String,
    val fareClass: String,
    val farePrice: Double,
    val farePriceWithCurrency: String,
    val discountInPercent: Byte,
    val infantsLeft: Int
)