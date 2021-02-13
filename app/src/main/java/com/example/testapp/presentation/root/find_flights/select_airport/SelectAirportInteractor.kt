package com.example.testapp.presentation.root.find_flights.select_airport

import com.example.domain.entity.station.Station
import com.example.domain.entity.station.StationDescription
import com.example.domain.entity.station.Stations
import com.example.testapp.presentation.root.find_flights.entities.AirPortType
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Coordinates Business Logic for [SelectAirportScope].
 */
@RibInteractor
class SelectAirportInteractor :
    Interactor<SelectAirportInteractor.SelectAirportPresenter, SelectAirportRouter>() {

    @Inject
    lateinit var presenter: SelectAirportPresenter
    @Inject
    lateinit var airPortType: AirPortType
    @Inject
    lateinit var stations: Stations
    @Inject
    lateinit var ribListener: Listener

    private val compositeDisposable = CompositeDisposable()

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        setupRx()
        updateAirports()
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
            presenter.onSearchFieldChanged().subscribe { updateAirports(it.toString()) },
            presenter.onAirportSelected().subscribe {
                ribListener.onAirPortSelected(airPortType, it)
                handleBackPress()
            }
        )
    }

    private fun updateAirports(keyword: String = "") {
        presenter.updateAirports(
            if (keyword.isBlank()) {
                stations.stationsList
            } else {
                stations.stationsList.filter { station ->
                    station.keywords.any { it.startsWith(keyword, true) }
                }
            }
        )
    }

    interface Listener {
        fun onClose()
        fun onAirPortSelected(airPortType: AirPortType, stationDescription: StationDescription)
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface SelectAirportPresenter {
        fun updateAirports(stations: List<Station>)

        fun onClose(): Observable<Unit>
        fun onSearchFieldChanged(): Observable<CharSequence>
        fun onAirportSelected(): Observable<StationDescription>
    }
}
