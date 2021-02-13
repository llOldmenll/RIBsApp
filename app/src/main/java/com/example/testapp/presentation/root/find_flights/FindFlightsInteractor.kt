package com.example.testapp.presentation.root.find_flights

import com.example.domain.entity.request.GetAvailableFlightsRequest
import com.example.domain.entity.station.StationDescription
import com.example.domain.use_case.GetFlightOptionsUseCase
import com.example.domain.utils.toDate
import com.example.domain.utils.toFormattedDate
import com.example.testapp.presentation.root.find_flights.entities.AirPortType
import com.example.testapp.presentation.root.find_flights.entities.AirPortType.*
import com.example.testapp.presentation.root.find_flights.select_airport.SelectAirportInteractor
import com.jakewharton.rxrelay2.BehaviorRelay
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.Calendar
import java.util.Date
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

    companion object {
        private const val REQUEST_DATE_FORMAT = "yyyy-MM-dd"
        private const val PREVIEW_DATE_FORMAT = "dd MMMM YYYY"
    }

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
            presenter.onChooseDate().subscribe { presenter.selectDate(getChosenDate()) },
            presenter.onSearchFlights().subscribe { searchFlights() },
            presenter.onSelectPassengers()
                .subscribe { router.attachSelectPassengersScreen(searchFlightsRequest.passengers) },
            presenter.onDateSelected().subscribe {
                searchFlightsRequest.dateOut = it.toFormattedDate(REQUEST_DATE_FORMAT)
                presenter.updateDateOut(it.toFormattedDate(PREVIEW_DATE_FORMAT))
            }
        )
    }

    private fun updateSearchFlightsParams() {
        updateOrigin()
        updateDestination()
        searchFlightsRequest.apply {
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

    private fun updateOrigin() =
        searchFlightsRequest.apply { presenter.updateOrigin(originCity, originCode) }

    private fun updateDestination() =
        searchFlightsRequest.apply { presenter.updateDestination(destinationCity, destinationCode) }

    private fun getChosenDate(): Date {
        return if (searchFlightsRequest.dateOut.isBlank()) Calendar.getInstance().time
        else searchFlightsRequest.dateOut.toDate(REQUEST_DATE_FORMAT)
    }

    inner class SelectAirportListener : SelectAirportInteractor.Listener {
        override fun onClose() = router.detachCurrentChild()

        override fun onAirPortSelected(
            airPortType: AirPortType,
            stationDescription: StationDescription,
        ) {
            if (airPortType == ORIGIN) {
                searchFlightsRequest.apply {
                    originCity = stationDescription.name
                    originCode = stationDescription.code
                }
                updateOrigin()
            } else {
                searchFlightsRequest.apply {
                    destinationCity = stationDescription.name
                    destinationCode = stationDescription.code
                }
                updateDestination()
            }
        }
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface FindFlightsPresenter {
        fun showError(error: Throwable)
        fun selectDate(chosenDate: Date)
        fun updateOrigin(city: String, code: String)
        fun updateDestination(city: String, code: String)
        fun updateDateOut(date: String)
        fun updatePassengers(adults: Int, teens: Int, children: Int)

        fun progressVisibility(): BehaviorRelay<Boolean>
        fun searchAbilityState(): BehaviorRelay<Boolean>

        fun onChooseOrigin(): Observable<Any>
        fun onChooseDestination(): Observable<Any>
        fun onChooseDate(): Observable<Any>
        fun onSelectPassengers(): Observable<Any>
        fun onSearchFlights(): Observable<Any>
        fun onDateSelected(): Observable<Date>
    }
}
