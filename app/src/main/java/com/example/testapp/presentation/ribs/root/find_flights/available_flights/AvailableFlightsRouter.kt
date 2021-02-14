package com.example.testapp.presentation.ribs.root.find_flights.available_flights

import com.example.domain.entity.flight.FlightOption
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link AvailableFlightsBuilder.AvailableFlightsScope}.
 */
class AvailableFlightsRouter(
    view: AvailableFlightsView,
    interactor: AvailableFlightsInteractor,
    component: AvailableFlightsBuilder.Component,
) : ViewRouter<AvailableFlightsView, AvailableFlightsInteractor, AvailableFlightsBuilder.Component>(
    view, interactor, component) {

    override fun handleBackPress(): Boolean = interactor.handleBackPress()

    fun attachFlightSummary(flightOption: FlightOption) {
        // TODO: Attach flight summary RIB
    }
}
