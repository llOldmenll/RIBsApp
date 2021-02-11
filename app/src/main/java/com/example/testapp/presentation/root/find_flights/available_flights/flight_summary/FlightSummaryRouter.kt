package com.example.testapp.presentation.root.find_flights.available_flights.flight_summary

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link FlightSummaryBuilder.FlightSummaryScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class FlightSummaryRouter(
    view: FlightSummaryView,
    interactor: FlightSummaryInteractor,
    component: FlightSummaryBuilder.Component) : ViewRouter<FlightSummaryView, FlightSummaryInteractor, FlightSummaryBuilder.Component>(view, interactor, component)
