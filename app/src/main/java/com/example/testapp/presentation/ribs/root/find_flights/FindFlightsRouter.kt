package com.example.testapp.presentation.ribs.root.find_flights

import com.example.domain.entity.flight.FlightOptions
import com.example.domain.entity.request.PassengersRequest
import com.example.testapp.presentation.ribs.root.find_flights.available_flights.AvailableFlightsBuilder
import com.example.testapp.entity.AirPortType
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
    private val selectPassengersBuilder: SelectPassengersBuilder,
    private val availableFlightsBuilder: AvailableFlightsBuilder,
) : ViewRouter<FindFlightsView, FindFlightsInteractor, FindFlightsBuilder.Component>(
    view,
    interactor,
    component
) {

    private var currentChild: Router<*, *>? = null

    fun attachSelectAirPortScreen(airPortType: AirPortType) =
        attachCurrentChild(selectAirportBuilder.build(view, airPortType))

    fun attachSelectPassengersScreen(passengersRequest: PassengersRequest) =
        attachCurrentChild(selectPassengersBuilder.build(view, passengersRequest))

    fun attachAvailableFlightsScreen(flightOptions: FlightOptions) =
        attachCurrentChild(availableFlightsBuilder.build(view, flightOptions))

    fun detachCurrentChild() {
        currentChild?.let {
            detachChild(it)
            if (it is ViewRouter<*, *, *>) view.removeView(it.view)
            currentChild = null
        }
    }

    private fun attachCurrentChild(child: Router<*, *>?) {
        currentChild = child.let {
            attachChild(it)
            if (it is ViewRouter<*, *, *>) view.addView(it.view)
            it
        }
    }

    override fun handleBackPress(): Boolean = currentChild?.handleBackPress() ?: false
}
