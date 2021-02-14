package com.example.testapp.mapper

import com.example.domain.utils.toFormattedDate
import com.example.domain.entity.flight.FlightOption
import com.example.domain.mapper.Mapper
import com.example.testapp.entity.FlightOptionVM

class FlightOptionToFlightOptionVMMapper : Mapper<FlightOption, FlightOptionVM> {

    companion object {
        private const val INPUT_DATE_TYPE = "yyyy-MM-dd'T'HH:mm:ss"
        private const val FLIGHT_DATE_TYPE = "EEE, dd MMM yyyy"
        private const val TIME_OUT_IN_DATE_TYPE = "HH:mm"
    }

    override fun map(from: FlightOption): FlightOptionVM = FlightOptionVM(
        from.dateOut.toFormattedDate(INPUT_DATE_TYPE, FLIGHT_DATE_TYPE),
        from.dateOut.toFormattedDate(INPUT_DATE_TYPE, TIME_OUT_IN_DATE_TYPE),
        from.dateIn.toFormattedDate(INPUT_DATE_TYPE, TIME_OUT_IN_DATE_TYPE),
        from.duration,
        from.flightNumber,
        from.farePriceWithCurrency
    )
}