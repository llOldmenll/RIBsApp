package com.example.testapp.presentation.root.find_flights.available_flights

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.RouterHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AvailableFlightsRouterTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var component: AvailableFlightsBuilder.Component
  @Mock internal lateinit var interactor: AvailableFlightsInteractor
  @Mock internal lateinit var view: AvailableFlightsView

  private var router: AvailableFlightsRouter? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    router = AvailableFlightsRouter(view, interactor, component)
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

