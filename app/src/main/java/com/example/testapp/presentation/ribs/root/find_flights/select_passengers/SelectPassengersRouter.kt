package com.example.testapp.presentation.ribs.root.find_flights.select_passengers

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link SelectPassengersBuilder.SelectPassengersScope}.
 */
class SelectPassengersRouter(
    view: SelectPassengersView,
    interactor: SelectPassengersInteractor,
    component: SelectPassengersBuilder.Component,
) : ViewRouter<SelectPassengersView, SelectPassengersInteractor, SelectPassengersBuilder.Component>(
    view,
    interactor,
    component) {
    override fun handleBackPress(): Boolean = interactor.handleBackPress()
}
