package com.example.testapp.presentation.ribs.root.find_flights.available_flights

import com.example.domain.entity.flight.FlightOptions
import com.example.testapp.mapper.FlightOptionToFlightOptionVMMapper
import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AvailableFlightsInteractorTest : RibTestBasePlaceholder() {

    @Mock
    internal lateinit var presenter: AvailableFlightsInteractor.AvailableFlightsPresenter
    @Mock
    internal lateinit var router: AvailableFlightsRouter
    @Mock
    internal lateinit var ribListener: AvailableFlightsInteractor.Listener

    private var interactor: AvailableFlightsInteractor? = null
    private var fakeFlightOptions = FlightOptions()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        interactor = TestAvailableFlightsInteractor.create(
            presenter,
            fakeFlightOptions,
            ribListener,
            FlightOptionToFlightOptionVMMapper()
        )
    }

    /**
     * TODO: Delete this example and add real tests.
     */
    @Test
    fun anExampleTest_withSomeConditions_shouldPass() {
        // Use InteractorHelper to drive your interactor's lifecycle.
        InteractorHelper.attach<AvailableFlightsInteractor.AvailableFlightsPresenter, AvailableFlightsRouter>(
          interactor!!,
          presenter,
          router,
          null)
        InteractorHelper.detach(interactor!!)

        throw RuntimeException("Remove this test and add real tests.")
    }
}