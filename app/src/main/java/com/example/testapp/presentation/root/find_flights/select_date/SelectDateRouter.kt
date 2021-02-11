package com.example.testapp.presentation.root.find_flights.select_date

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link SelectDateBuilder.SelectDateScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class SelectDateRouter(
    view: SelectDateView,
    interactor: SelectDateInteractor,
    component: SelectDateBuilder.Component) : ViewRouter<SelectDateView, SelectDateInteractor, SelectDateBuilder.Component>(view, interactor, component)
