package com.example.testapp.presentation.root.find_flights

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FindFlightsInteractorTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var presenter: FindFlightsInteractor.FindFlightsPresenter
  @Mock internal lateinit var router: FindFlightsRouter

  private var interactor: FindFlightsInteractor? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    interactor = TestFindFlightsInteractor.create(presenter)
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  fun anExampleTest_withSomeConditions_shouldPass() {
    // Use InteractorHelper to drive your interactor's lifecycle.
    InteractorHelper.attach<FindFlightsInteractor.FindFlightsPresenter, FindFlightsRouter>(interactor!!, presenter, router, null)
    InteractorHelper.detach(interactor!!)

    throw RuntimeException("Remove this test and add real tests.")
  }
}