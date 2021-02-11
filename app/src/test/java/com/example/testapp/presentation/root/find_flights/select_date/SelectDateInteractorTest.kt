package com.example.testapp.presentation.root.find_flights.select_date

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SelectDateInteractorTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var presenter: SelectDateInteractor.SelectDatePresenter
  @Mock internal lateinit var router: SelectDateRouter

  private var interactor: SelectDateInteractor? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    interactor = TestSelectDateInteractor.create(presenter)
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  fun anExampleTest_withSomeConditions_shouldPass() {
    // Use InteractorHelper to drive your interactor's lifecycle.
    InteractorHelper.attach<SelectDateInteractor.SelectDatePresenter, SelectDateRouter>(interactor!!, presenter, router, null)
    InteractorHelper.detach(interactor!!)

    throw RuntimeException("Remove this test and add real tests.")
  }
}