package com.example.testapp.presentation.root.find_flights

import com.example.domain.entity.flight.FlightOptions
import com.example.domain.use_case.GetFlightOptionsUseCase
import com.example.testapp.helper.RxImmediateSchedulerRule
import com.nhaarman.mockitokotlin2.any
import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper
import io.reactivex.Single

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FindFlightsInteractorTest : RibTestBasePlaceholder() {

    @Mock
    internal lateinit var presenter: FindFlightsInteractor.FindFlightsPresenter
    @Mock
    internal lateinit var router: FindFlightsRouter
    @Mock
    internal lateinit var getFlightOptionsUseCase: GetFlightOptionsUseCase
    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

    private var interactor: FindFlightsInteractor? = null
    private val fakeFlightOptions by lazy { FlightOptions(listOf()) }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        interactor = TestFindFlightsInteractor.create(presenter, getFlightOptionsUseCase)

        Mockito.`when`(getFlightOptionsUseCase.execute(any()))
            .thenReturn(Single.just(fakeFlightOptions))
    }

    /**
     * TODO: Delete this example and add real tests.
     */
    @Test
    fun anExampleTest_withSomeConditions_shouldPass() {
        // Use InteractorHelper to drive your interactor's lifecycle.
        InteractorHelper.attach<FindFlightsInteractor.FindFlightsPresenter, FindFlightsRouter>(
            interactor!!,
            presenter,
            router,
            null)
        InteractorHelper.detach(interactor!!)

        throw RuntimeException("Remove this test and add real tests.")
    }
}