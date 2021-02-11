package com.example.testapp.presentation.root.find_flights.select_airport

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SelectAirportInteractorTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var presenter: SelectAirportInteractor.SelectAirportPresenter
  @Mock internal lateinit var router: SelectAirportRouter

  private var interactor: SelectAirportInteractor? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    interactor = TestSelectAirportInteractor.create(presenter)
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  fun anExampleTest_withSomeConditions_shouldPass() {
    // Use InteractorHelper to drive your interactor's lifecycle.
    InteractorHelper.attach<SelectAirportInteractor.SelectAirportPresenter, SelectAirportRouter>(interactor!!, presenter, router, null)
    InteractorHelper.detach(interactor!!)

    throw RuntimeException("Remove this test and add real tests.")
  }
}