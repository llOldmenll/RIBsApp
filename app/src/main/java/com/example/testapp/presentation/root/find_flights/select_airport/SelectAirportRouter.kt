package com.example.testapp.presentation.root.find_flights.select_airport

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link SelectAirportBuilder.SelectAirportScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class SelectAirportRouter(
    view: SelectAirportView,
    interactor: SelectAirportInteractor,
    component: SelectAirportBuilder.Component) : ViewRouter<SelectAirportView, SelectAirportInteractor, SelectAirportBuilder.Component>(view, interactor, component)
