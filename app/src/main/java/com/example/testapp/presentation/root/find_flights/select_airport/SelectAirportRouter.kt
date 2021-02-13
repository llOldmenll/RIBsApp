package com.example.testapp.presentation.root.find_flights.select_airport

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link SelectAirportBuilder.SelectAirportScope}.
 */
class SelectAirportRouter(
    view: SelectAirportView,
    interactor: SelectAirportInteractor,
    component: SelectAirportBuilder.Component,
) : ViewRouter<SelectAirportView, SelectAirportInteractor, SelectAirportBuilder.Component>(view,
    interactor, component) {

    override fun handleBackPress(): Boolean = interactor.handleBackPress()
}
