package com.example.testapp.presentation.ribs.root.find_flights.select_airport

import com.example.domain.entity.station.Stations
import com.example.testapp.entity.AirPortType
import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SelectAirportInteractorTest : RibTestBasePlaceholder() {

    @Mock
    internal lateinit var presenter: SelectAirportInteractor.SelectAirportPresenter
    @Mock
    internal lateinit var router: SelectAirportRouter
    @Mock
    internal lateinit var ribListener: SelectAirportInteractor.Listener

    private var interactor: SelectAirportInteractor? = null
    private val fakeAirportType = AirPortType.ORIGIN
    private val fakeStations = Stations(listOf())

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        interactor = TestSelectAirportInteractor.create(
            presenter,
            fakeAirportType,
            fakeStations,
            ribListener
        )
    }

    /**
     * TODO: Delete this example and add real tests.
     */
    @Test
    fun anExampleTest_withSomeConditions_shouldPass() {
        // Use InteractorHelper to drive your interactor's lifecycle.
        InteractorHelper.attach<SelectAirportInteractor.SelectAirportPresenter, SelectAirportRouter>(
            interactor!!,
            presenter,
            router,
            null)
        InteractorHelper.detach(interactor!!)

        throw RuntimeException("Remove this test and add real tests.")
    }
}