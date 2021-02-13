package com.example.testapp.presentation.root.find_flights

import com.example.domain.entity.request.GetAvailableFlightsRequest
import com.example.domain.use_case.GetFlightOptionsUseCase
import com.example.testapp.presentation.root.find_flights.entities.AirPortType
import com.example.testapp.presentation.root.find_flights.entities.AirPortType.*
import com.jakewharton.rxrelay2.BehaviorRelay
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Coordinates Business Logic for [FindFlightsScope].
 */
@RibInteractor
class FindFlightsInteractor :
    Interactor<FindFlightsInteractor.FindFlightsPresenter, FindFlightsRouter>() {

    @Inject
    lateinit var presenter: FindFlightsPresenter

    @Inject
    lateinit var getFlightOptionsUseCase: GetFlightOptionsUseCase

    private val compositeDisposable = CompositeDisposable()
    private val searchFlightsRequest = GetAvailableFlightsRequest()

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        updateSearchFlightsParams()
        setupRx()
    }

    override fun willResignActive() {
        compositeDisposable.clear()
        super.willResignActive()
    }

    private fun setupRx() {
        compositeDisposable.addAll(
            presenter.onChooseOrigin().subscribe { selectAirPort(ORIGIN) },
            presenter.onChooseDestination().subscribe { selectAirPort(DESTINATION) },
            presenter.onChooseDate().subscribe { router.attachSelectDateScreen() },
            presenter.onSelectPassengers()
                .subscribe { router.attachSelectPassengersScreen(searchFlightsRequest.passengers) },
            presenter.onSearchFlights().subscribe { searchFlights() }
        )
    }

    private fun updateSearchFlightsParams() {
        searchFlightsRequest.apply {
            presenter.updateOrigin(origin)
            presenter.updateDestination(destination)
            presenter.updateDateOut(dateOut)
            passengers.apply { presenter.updatePassengers(adults, teens, children) }
        }
    }

    private fun searchFlights() {
        compositeDisposable.add(
            getFlightOptionsUseCase.execute(searchFlightsRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { presenter.progressVisibility().accept(true) }
                .doFinally { presenter.progressVisibility().accept(false) }
                .subscribe({ router.attachAvailableFlightsScreen(it) }, { presenter.showError(it) })
        )
    }

    private fun selectAirPort(type: AirPortType) = router.attachSelectAirPortScreen(type)

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface FindFlightsPresenter {
        fun showError(error: Throwable)
        fun updateOrigin(origin: String)
        fun updateDestination(destination: String)
        fun updateDateOut(date: String)
        fun updatePassengers(adults: Int, teens: Int, children: Int)

        fun progressVisibility(): BehaviorRelay<Boolean>
        fun searchAbilityState(): BehaviorRelay<Boolean>

        fun onChooseOrigin(): Observable<Any>
        fun onChooseDestination(): Observable<Any>
        fun onChooseDate(): Observable<Any>
        fun onSelectPassengers(): Observable<Any>
        fun onSearchFlights(): Observable<Any>
    }
}
