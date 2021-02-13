package com.example.data.repository

import com.example.data.entity.flight.FlightsAvailabilityEntity
import com.example.data.network.service.FlightNetworkService
import com.example.domain.entity.flight.FlightOptions
import com.example.domain.entity.request.GetAvailableFlightsRequest
import com.example.domain.mapper.Mapper
import com.example.domain.repository.FlightRepository
import io.reactivex.Single

class FlightRepositoryImpl(
    private val flightNetworkService: FlightNetworkService,
    private val flightsAvailabilityMapper: Mapper<FlightsAvailabilityEntity, FlightOptions>
) : FlightRepository {

    override fun getAvailableFlights(request: GetAvailableFlightsRequest): Single<FlightOptions> {
        return flightNetworkService.getAvailableFlights(
            request.origin,
            request.destination,
            request.dateOut,
            request.passengers.adults,
            request.passengers.teens,
            request.passengers.children
        ).map { flightsAvailabilityMapper.map(it) }
    }
}