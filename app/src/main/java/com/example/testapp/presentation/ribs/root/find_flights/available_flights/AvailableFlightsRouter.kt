package com.example.testapp.presentation.ribs.root.find_flights.available_flights

import com.example.domain.entity.flight.FlightOption
import com.example.testapp.presentation.ribs.root.find_flights.available_flights.flight_summary.FlightSummaryBuilder
import com.uber.rib.core.Router
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link AvailableFlightsBuilder.AvailableFlightsScope}.
 */
class AvailableFlightsRouter(
    view: AvailableFlightsView,
    interactor: AvailableFlightsInteractor,
    component: AvailableFlightsBuilder.Component,
    private val flightSummaryBuilder: FlightSummaryBuilder,
) : ViewRouter<AvailableFlightsView, AvailableFlightsInteractor, AvailableFlightsBuilder.Component>(
    view, interactor, component) {

    private var currentChild: Router<*, *>? = null

    fun attachFlightSummary(flightOption: FlightOption) =
        attachCurrentChild(flightSummaryBuilder.build(view, flightOption))

    fun detachCurrentChild() {
        currentChild?.let {
            detachChild(it)
            if (it is ViewRouter<*, *, *>) view.removeView(it.view)
            currentChild = null
        }
    }

    private fun attachCurrentChild(child: Router<*, *>?) {
        currentChild = child.let {
            attachChild(it)
            if (it is ViewRouter<*, *, *>) view.addView(it.view)
            it
        }
    }

    override fun handleBackPress(): Boolean =
        currentChild?.handleBackPress() ?: interactor.handleBackPress()
}
