package com.example.domain.use_case

import com.example.domain.entity.flight.FlightOptions
import com.example.domain.entity.request.GetAvailableFlightsRequest
import com.example.domain.exception.NullableDataException
import com.example.domain.repository.FlightRepository
import com.example.domain.use_case.base.SingleUseCase
import io.reactivex.Single

class GetFlightOptionsUseCase(
    private val flightRepository: FlightRepository
) : SingleUseCase<GetAvailableFlightsRequest, FlightOptions> {
    override fun execute(data: GetAvailableFlightsRequest?): Single<FlightOptions> = data?.let {
        flightRepository.getAvailableFlights(it)
    } ?: Single.error(NullableDataException())
}