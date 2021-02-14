package com.example.testapp.presentation.ribs.root.find_flights.available_flights

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [AvailableFlightsScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class AvailableFlightsInteractor : Interactor<AvailableFlightsInteractor.AvailableFlightsPresenter, AvailableFlightsRouter>() {

  @Inject
  lateinit var presenter: AvailableFlightsPresenter

  override fun didBecomeActive(savedInstanceState: Bundle?) {
    super.didBecomeActive(savedInstanceState)

    // TODO: Add attachment logic here (RxSubscriptions, etc.).
  }

  override fun willResignActive() {
    super.willResignActive()

    // TODO: Perform any required clean up here, or delete this method entirely if not needed.
  }

  /**
   * Presenter interface implemented by this RIB's view.
   */
  interface AvailableFlightsPresenter
}
