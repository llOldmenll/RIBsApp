package com.example.domain.entity.request

data class GetAvailableFlightsRequest(
    var origin: String = "",
    var destination: String = "",
    var dateOut: String = "",
    var passengers: PassengersRequest = PassengersRequest()
) {
    fun isValid(): Boolean =
        origin.isNotBlank() && destination.isNotBlank() && dateOut.isNotBlank() && passengers.isValid()
}