package com.example.testapp.presentation.ribs.root.find_flights

import com.example.domain.entity.flight.FlightOptions
import com.example.domain.entity.request.PassengersRequest
import com.example.testapp.presentation.ribs.root.find_flights.entities.AirPortType
import com.example.testapp.presentation.ribs.root.find_flights.select_airport.SelectAirportBuilder
import com.example.testapp.presentation.ribs.root.find_flights.select_passengers.SelectPassengersBuilder
import com.uber.rib.core.Router
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link FindFlightsBuilder.FindFlightsScope}.
 */
class FindFlightsRouter(
    view: FindFlightsView,
    interactor: FindFlightsInteractor,
    component: FindFlightsBuilder.Component,
    private val selectAirportBuilder: SelectAirportBuilder,
    private val selectPassengersBuilder: SelectPassengersBuilder
) : ViewRouter<FindFlightsView, FindFlightsInteractor, FindFlightsBuilder.Component>(
    view,
    interactor,
    component
) {

    private var currentChild: Router<*, *>? = null

    fun attachSelectAirPortScreen(airPortType: AirPortType) {
        currentChild = selectAirportBuilder.build(view, airPortType).let {
            attachChild(it)
            view.addView(it.view)
            it
        }
    }

    fun attachSelectPassengersScreen(passengersRequest: PassengersRequest) {
        currentChild = selectPassengersBuilder.build(view, passengersRequest).let {
            attachChild(it)
            view.addView(it.view)
            it
        }
    }

    fun attachAvailableFlightsScreen(flightOptions: FlightOptions) {
        // TODO: Add SelectPassengers RIB attachment
    }

    fun detachCurrentChild() {
        currentChild?.let {
            detachChild(it)
            if (it is ViewRouter<*, *, *>) view.removeView(it.view)
            currentChild = null
        }
    }

    override fun handleBackPress(): Boolean = currentChild?.handleBackPress() ?: false
}
