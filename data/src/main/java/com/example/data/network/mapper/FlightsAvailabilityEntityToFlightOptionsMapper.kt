package com.example.data.network.mapper

import com.example.data.entity.flight.FareEntity
import com.example.data.entity.flight.FlightsAvailabilityEntity
import com.example.data.entity.flight.TripEntity
import com.example.domain.entity.flight.FlightOption
import com.example.domain.entity.flight.FlightOptions
import com.example.domain.mapper.Mapper
import com.example.domain.utils.toShortString
import kotlin.math.roundToInt

class FlightsAvailabilityEntityToFlightOptionsMapper :
    Mapper<FlightsAvailabilityEntity, FlightOptions> {

    override fun map(from: FlightsAvailabilityEntity): FlightOptions {
        val currency = from.currency ?: ""
        val flightOptionsList = mutableListOf<FlightOption>()
        from.trips?.forEach { flightOptionsList.addAll(provideFlightOptions(currency, it)) }

        return FlightOptions(flightOptionsList)
    }

    private fun provideFlightOptions(currency: String, trip: TripEntity): List<FlightOption> {
        val flightOptionsList = mutableListOf<FlightOption>()
        var farePrice = 0.0
        var discountInPercent = 0

        trip.dates?.forEach { flightDate ->
            flightDate.flights?.forEach { flightOption ->
                updateFarePriceAndDiscount(
                    flightOption.regularFare?.fares ?: listOf(),
                    { farePrice = it }, { discountInPercent = it }
                )
                flightOptionsList.add(
                    FlightOption(
                        flightOption.flightNumber ?: "",
                        provideOrigin(trip),
                        provideDestination(trip),
                        flightOption.time?.get(0) ?: flightDate.dateOut ?: "",
                        flightOption.time?.get(1) ?: "",
                        flightOption.duration ?: "",
                        flightOption.regularFare?.fareClass ?: "",
                        farePrice,
                        "${farePrice.toShortString()} $currency",
                        discountInPercent.toByte(),
                        flightOption.infantsLeft ?: 0
                    )
                )
            }
        }

        return flightOptionsList
    }

    private fun updateFarePriceAndDiscount(
        fare: List<FareEntity>,
        updateFarePrice: (Double) -> Unit,
        updateDiscount: (Int) -> Unit,
    ) {
        var totalPublishedFare = 0.0
        var totalDiscountAmount = 0.0

        fare.forEach {
            totalPublishedFare += (it.publishedFare ?: 0.0) * (it.count ?: 1)
            totalDiscountAmount += (it.discountAmount ?: 0.0) * (it.count ?: 1)
        }

        updateFarePrice(totalPublishedFare - totalDiscountAmount)
        updateDiscount((totalDiscountAmount * 100 / totalPublishedFare).roundToInt())
    }

    private fun provideOrigin(trip: TripEntity): String = "${trip.originName} (${trip.origin})"

    private fun provideDestination(trip: TripEntity): String =
        "${trip.destinationName} (${trip.destination})"
}