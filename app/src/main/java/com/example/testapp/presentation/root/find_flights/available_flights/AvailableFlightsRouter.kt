package com.example.testapp.presentation.root.find_flights.available_flights

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link AvailableFlightsBuilder.AvailableFlightsScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class AvailableFlightsRouter(
    view: AvailableFlightsView,
    interactor: AvailableFlightsInteractor,
    component: AvailableFlightsBuilder.Component) : ViewRouter<AvailableFlightsView, AvailableFlightsInteractor, AvailableFlightsBuilder.Component>(view, interactor, component)
