package com.example.testapp.presentation.root.find_flights

import com.example.domain.entity.flight.FlightOptions
import com.example.domain.entity.request.PassengersRequest
import com.example.testapp.presentation.root.find_flights.entities.AirPortType
import com.uber.rib.core.Router
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link FindFlightsBuilder.FindFlightsScope}.
 */
class FindFlightsRouter(
    view: FindFlightsView,
    interactor: FindFlightsInteractor,
    component: FindFlightsBuilder.Component
) : ViewRouter<FindFlightsView, FindFlightsInteractor, FindFlightsBuilder.Component>(
    view,
    interactor,
    component
) {

    private var currentChild: Router<*, *>? = null

    fun attachSelectAirPortScreen(airPortType: AirPortType) {
        // TODO: Add SelectAirPort RIB attachment
    }

    fun attachSelectDateScreen() {
        // TODO: Add SelectDate RIB attachment
    }

    fun attachSelectPassengersScreen(passengersRequest: PassengersRequest) {
        // TODO: Add SelectPassengers RIB attachment
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
}
