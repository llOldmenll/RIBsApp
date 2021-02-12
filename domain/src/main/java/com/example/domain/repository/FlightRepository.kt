package com.example.domain.repository

import com.example.domain.entity.flight.FlightOptions
import com.example.domain.entity.request.GetAvailableFlightsRequest
import io.reactivex.Single

/**
 * Used for interaction with flights data
 */
interface FlightRepository {
    /**
     * Get all available flights
     *
     * @param request contains data for flights search
     * @return FlightOption
     */
    fun getAvailableFlights(request: GetAvailableFlightsRequest): Single<FlightOptions>
}