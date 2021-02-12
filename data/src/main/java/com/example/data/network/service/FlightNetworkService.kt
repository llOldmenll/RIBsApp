package com.example.data.network.service

import com.example.data.entity.flight.FlightsAvailabilityEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightNetworkService {
    @GET("booking/v4/en-gb/Availability")
    fun getAvailableFlights(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("dateout") dateOut: String,
        @Query("adt") adults: Int,
        @Query("teen") teens: Int,
        @Query("chd") children: Int,
        @Query("roundtrip") roundTrip: Boolean = false,
        @Query("ToUs") toUs: String = "AGREED"
    ): Single<FlightsAvailabilityEntity>
}