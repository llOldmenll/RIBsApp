package com.example.testapp.presentation.root.find_flights.select_passengers

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link SelectPassengersBuilder.SelectPassengersScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class SelectPassengersRouter(
    view: SelectPassengersView,
    interactor: SelectPassengersInteractor,
    component: SelectPassengersBuilder.Component) : ViewRouter<SelectPassengersView, SelectPassengersInteractor, SelectPassengersBuilder.Component>(view, interactor, component)
