package com.example.testapp.mapper

import com.example.domain.entity.flight.FlightOption
import com.example.domain.mapper.Mapper
import com.example.domain.utils.asPercents
import com.example.testapp.R
import com.example.testapp.entity.FlightSummaryVM

class FlightOptionToFlightSummariesListVMMapper : Mapper<FlightOption, List<FlightSummaryVM>> {

    override fun map(from: FlightOption): List<FlightSummaryVM> =
        arrayListOf<FlightSummaryVM>().apply {
            add(FlightSummaryVM(R.string.origin, from.origin))
            add(FlightSummaryVM(R.string.destination, from.destination))
            add(FlightSummaryVM(R.string.infantsLeft, from.infantsLeft.toString()))
            add(FlightSummaryVM(R.string.fareClass, from.fareClass))
            add(FlightSummaryVM(R.string.discount, from.discountInPercent.asPercents()))
        }
}