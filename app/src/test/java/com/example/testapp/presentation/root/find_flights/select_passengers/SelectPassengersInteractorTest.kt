package com.example.testapp.presentation.root.find_flights.select_passengers

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SelectPassengersInteractorTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var presenter: SelectPassengersInteractor.SelectPassengersPresenter
  @Mock internal lateinit var router: SelectPassengersRouter

  private var interactor: SelectPassengersInteractor? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    interactor = TestSelectPassengersInteractor.create(presenter)
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  fun anExampleTest_withSomeConditions_shouldPass() {
    // Use InteractorHelper to drive your interactor's lifecycle.
    InteractorHelper.attach<SelectPassengersInteractor.SelectPassengersPresenter, SelectPassengersRouter>(interactor!!, presenter, router, null)
    InteractorHelper.detach(interactor!!)

    throw RuntimeException("Remove this test and add real tests.")
  }
}