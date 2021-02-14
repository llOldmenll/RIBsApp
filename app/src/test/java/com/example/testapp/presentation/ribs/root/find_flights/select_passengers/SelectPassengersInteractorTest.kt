package com.example.testapp.presentation.ribs.root.find_flights.select_passengers

import com.example.domain.entity.request.PassengersRequest
import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SelectPassengersInteractorTest : RibTestBasePlaceholder() {

    @Mock
    internal lateinit var presenter: SelectPassengersInteractor.SelectPassengersPresenter
    @Mock
    internal lateinit var router: SelectPassengersRouter
    @Mock
    internal lateinit var ribList: SelectPassengersInteractor.Listener

    private var interactor: SelectPassengersInteractor? = null
    private var fakePassengers = PassengersRequest()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        interactor = TestSelectPassengersInteractor.create(presenter, fakePassengers, ribList)
    }

    /**
     * TODO: Delete this example and add real tests.
     */
    @Test
    fun anExampleTest_withSomeConditions_shouldPass() {
        // Use InteractorHelper to drive your interactor's lifecycle.
        InteractorHelper.attach<SelectPassengersInteractor.SelectPassengersPresenter, SelectPassengersRouter>(
            interactor!!,
            presenter,
            router,
            null)
        InteractorHelper.detach(interactor!!)

        throw RuntimeException("Remove this test and add real tests.")
    }
}