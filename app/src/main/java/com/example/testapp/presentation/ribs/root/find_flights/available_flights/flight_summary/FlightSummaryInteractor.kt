package com.example.testapp.presentation.ribs.root.find_flights.available_flights.flight_summary

import com.example.domain.entity.flight.FlightOption
import com.example.domain.mapper.Mapper
import com.example.testapp.entity.FlightSummaryVM
import com.example.testapp.presentation.ribs.base.BaseListener
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Coordinates Business Logic for [FlightSummaryScope].
 */
@RibInteractor
class FlightSummaryInteractor :
    Interactor<FlightSummaryInteractor.FlightSummaryPresenter, FlightSummaryRouter>() {

    @Inject
    lateinit var presenter: FlightSummaryPresenter

    @Inject
    lateinit var flightOption: FlightOption

    @Inject
    lateinit var ribListener: Listener

    @Inject
    lateinit var flightOptionToSummaryMapper: Mapper<FlightOption, List<FlightSummaryVM>>

    private val compositeDisposable = CompositeDisposable()

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        setupRx()
        updateFlightSummary()
    }

    override fun willResignActive() {
        compositeDisposable.clear()
        super.willResignActive()
    }

    override fun handleBackPress(): Boolean {
        ribListener.onClose()
        return true
    }

    private fun setupRx() {
        compositeDisposable.add(
            presenter.onClose().subscribe { handleBackPress() }
        )
    }

    private fun updateFlightSummary() =
        presenter.updateFlightSummary(flightOptionToSummaryMapper.map(flightOption))

    interface Listener : BaseListener

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface FlightSummaryPresenter {
        fun updateFlightSummary(summaries: List<FlightSummaryVM>)
        fun onClose(): Observable<Unit>
    }
}
