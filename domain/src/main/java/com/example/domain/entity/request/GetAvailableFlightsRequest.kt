package com.example.domain.entity.request

data class GetAvailableFlightsRequest(
    val origin: String,
    val destination: String,
    val dateOut: String,
    val adults: Int,
    val teens: Int,
    val children: Int
)