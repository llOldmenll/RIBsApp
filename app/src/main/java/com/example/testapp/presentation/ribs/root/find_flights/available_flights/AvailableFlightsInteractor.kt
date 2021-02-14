package com.example.testapp.presentation.ribs.root.find_flights.available_flights

import com.example.domain.entity.flight.FlightOption
import com.example.domain.entity.flight.FlightOptions
import com.example.domain.mapper.Mapper
import com.example.testapp.entity.FlightOptionVM
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Coordinates Business Logic for [AvailableFlightsScope].
 */
@RibInteractor
class AvailableFlightsInteractor :
    Interactor<AvailableFlightsInteractor.AvailableFlightsPresenter, AvailableFlightsRouter>() {

    @Inject
    lateinit var presenter: AvailableFlightsPresenter
    @Inject
    lateinit var flightOptions: FlightOptions
    @Inject
    lateinit var ribListener: Listener
    @Inject
    lateinit var flightOptionMapper: Mapper<FlightOption, FlightOptionVM>

    private val compositeDisposable = CompositeDisposable()

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        setupRx()
        initUI()
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
        compositeDisposable.addAll(
            presenter.onClose().subscribe { handleBackPress() },
            presenter.onFlightOptionSelected().subscribe { optionVM ->
                flightOptions.list.firstOrNull { option ->
                    option.flightNumber == optionVM.flightNumber
                }?.let { router.attachFlightSummary(it) }
            }
        )
    }

    private fun initUI() {
        presenter.updateFlightPath(flightOptions.path)
        updateFlightOptions()
    }

    private fun updateFlightOptions() = presenter.updateFlightOptions(
        flightOptions.list.sortedBy { it.farePrice }
            .map { flightOptionMapper.map(it) }
    )

    interface Listener {
        fun onClose()
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface AvailableFlightsPresenter {
        fun updateFlightPath(path: String)
        fun updateFlightOptions(flightOptions: List<FlightOptionVM>)

        fun onClose(): Observable<Unit>
        fun onFlightOptionSelected(): Observable<FlightOptionVM>
    }
}
