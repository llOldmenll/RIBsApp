package com.example.testapp.presentation.ribs.root.find_flights.available_flights.flight_summary

import com.example.domain.entity.flight.FlightOption
import com.example.testapp.mapper.FlightOptionToFlightSummariesListVMMapper
import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FlightSummaryInteractorTest : RibTestBasePlaceholder() {

    @Mock
    internal lateinit var presenter: FlightSummaryInteractor.FlightSummaryPresenter
    @Mock
    internal lateinit var router: FlightSummaryRouter
    @Mock
    internal lateinit var ribListener: FlightSummaryInteractor.Listener

    private var interactor: FlightSummaryInteractor? = null
    private var fakeFlightOption = FlightOption("", "", "", "",
        "", "", "", 0.0, "", 0, 0)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        interactor = TestFlightSummaryInteractor.create(presenter,
            fakeFlightOption,
            ribListener,
            FlightOptionToFlightSummariesListVMMapper())
    }

    /**
     * TODO: Delete this example and add real tests.
     */
    @Test
    fun anExampleTest_withSomeConditions_shouldPass() {
        // Use InteractorHelper to drive your interactor's lifecycle.
        InteractorHelper.attach<FlightSummaryInteractor.FlightSummaryPresenter, FlightSummaryRouter>(
            interactor!!,
            presenter,
            router,
            null)
        InteractorHelper.detach(interactor!!)

        throw RuntimeException("Remove this test and add real tests.")
    }
}