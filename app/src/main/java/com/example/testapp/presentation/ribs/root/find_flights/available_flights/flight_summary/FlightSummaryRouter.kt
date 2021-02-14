package com.example.testapp.presentation.ribs.root.find_flights.available_flights.flight_summary

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link FlightSummaryBuilder.FlightSummaryScope}.
 */
class FlightSummaryRouter(
    view: FlightSummaryView,
    interactor: FlightSummaryInteractor,
    component: FlightSummaryBuilder.Component,
) : ViewRouter<FlightSummaryView, FlightSummaryInteractor, FlightSummaryBuilder.Component>(view,
    interactor, component) {

    override fun handleBackPress(): Boolean = interactor.handleBackPress()
}
