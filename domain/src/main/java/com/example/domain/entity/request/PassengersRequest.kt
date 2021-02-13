package com.example.domain.entity.request

data class PassengersRequest(
    var adults: Int = 1,
    var teens: Int = 0,
    var children: Int = 0
) {
    fun isValid(): Boolean = adults > 0 || teens > 0 || children > 0
}