package com.example.testapp.presentation.root.find_flights

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.RouterHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FindFlightsRouterTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var component: FindFlightsBuilder.Component
  @Mock internal lateinit var interactor: FindFlightsInteractor
  @Mock internal lateinit var view: FindFlightsView

  private var router: FindFlightsRouter? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    router = FindFlightsRouter(view, interactor, component)
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

