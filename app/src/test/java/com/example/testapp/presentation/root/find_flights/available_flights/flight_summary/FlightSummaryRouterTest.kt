package com.example.testapp.presentation.root.find_flights.available_flights.flight_summary

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.RouterHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FlightSummaryRouterTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var component: FlightSummaryBuilder.Component
  @Mock internal lateinit var interactor: FlightSummaryInteractor
  @Mock internal lateinit var view: FlightSummaryView

  private var router: FlightSummaryRouter? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    router = FlightSummaryRouter(view, interactor, component)
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  fun anExampleTest_withSomeConditions_shouldPass() {
    // Use RouterHelper to drive your router's lifecycle.
    RouterHelper.attach(router!!)
    RouterHelper.detach(router!!)

    throw RuntimeException("Remove this test and add real tests.")
  }

}

