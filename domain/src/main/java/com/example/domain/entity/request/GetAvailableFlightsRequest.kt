package com.example.domain.entity.request

data class GetAvailableFlightsRequest(
    var originCity: String = "",
    var originCode: String = "",
    var destinationCity: String = "",
    var destinationCode: String = "",
    var dateOut: String = "",
    var passengers: PassengersRequest = PassengersRequest(),
) {
    fun isValid(): Boolean =
        originCode.isNotBlank() && destinationCode.isNotBlank() && dateOut.isNotBlank() && passengers.isValid()
}