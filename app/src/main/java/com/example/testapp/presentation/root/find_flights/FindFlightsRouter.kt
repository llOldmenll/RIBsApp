package com.example.testapp.presentation.root.find_flights

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link FindFlightsBuilder.FindFlightsScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class FindFlightsRouter(
    view: FindFlightsView,
    interactor: FindFlightsInteractor,
    component: FindFlightsBuilder.Component) : ViewRouter<FindFlightsView, FindFlightsInteractor, FindFlightsBuilder.Component>(view, interactor, component)
