package com.example.testapp.presentation.ribs.root.find_flights

import com.example.data.network.extensions.isNotFoundException
import com.example.domain.entity.flight.FlightOptions
import com.example.domain.entity.request.GetAvailableFlightsRequest
import com.example.domain.entity.request.PassengersRequest
import com.example.domain.entity.station.StationDescription
import com.example.domain.use_case.GetFlightOptionsUseCase
import com.example.domain.utils.toDate
import com.example.domain.utils.toFormattedDate
import com.example.testapp.presentation.ribs.root.find_flights.available_flights.AvailableFlightsInteractor
import com.example.testapp.entity.AirPortType
import com.example.testapp.entity.AirPortType.*
import com.example.testapp.presentation.ribs.root.find_flights.select_airport.SelectAirportInteractor
import com.example.testapp.presentation.ribs.root.find_flights.select_passengers.SelectPassengersInteractor
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
                updateSearchAbility()
            }
        )
    }

    private fun updateSearchFlightsParams() {
        updateOrigin()
        updateDestination()
        updatePassengers()
        searchFlightsRequest.apply {
            presenter.updateDateOut(
                dateOut.toFormattedDate(REQUEST_DATE_FORMAT, PREVIEW_DATE_FORMAT))
        }
        updateSearchAbility()
    }

    private fun searchFlights() {
        compositeDisposable.add(
            getFlightOptionsUseCase.execute(searchFlightsRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { presenter.updateProgressVisibility(true) }
                .doFinally { presenter.updateProgressVisibility(false) }
                .subscribe({ router.attachAvailableFlightsScreen(it) }, {
                    if (it.isNotFoundException()) router.attachAvailableFlightsScreen(FlightOptions())
                    else presenter.showError(it)
                })
        )
    }

    private fun selectAirPort(type: AirPortType) = router.attachSelectAirPortScreen(type)

    private fun updateOrigin() =
        searchFlightsRequest.apply { presenter.updateOrigin(originCity, originCode) }

    private fun updateDestination() =
        searchFlightsRequest.apply { presenter.updateDestination(destinationCity, destinationCode) }

    private fun updatePassengers() = searchFlightsRequest.apply {
        passengers.apply { presenter.updatePassengers(adults, teens, children) }
    }

    private fun getChosenDate(): Date {
        return if (searchFlightsRequest.dateOut.isBlank()) Calendar.getInstance().time
        else searchFlightsRequest.dateOut.toDate(REQUEST_DATE_FORMAT)
    }

    private fun updateSearchAbility() =
        presenter.updateSearchAbilityState(searchFlightsRequest.isValid())

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
            updateSearchAbility()
        }
    }

    inner class SelectPassengersListener : SelectPassengersInteractor.Listener {
        override fun onClose() = router.detachCurrentChild()

        override fun onPassengersSelected(passengers: PassengersRequest) {
            searchFlightsRequest.passengers = passengers
            updatePassengers()
            updateSearchAbility()
        }
    }

    inner class AvailableFlightsListener : AvailableFlightsInteractor.Listener {
        override fun onClose() = router.detachCurrentChild()
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

        fun updateProgressVisibility(isVisible: Boolean)
        fun updateSearchAbilityState(isEnabled: Boolean)

        fun onChooseOrigin(): Observable<Any>
        fun onChooseDestination(): Observable<Any>
        fun onChooseDate(): Observable<Any>
        fun onSelectPassengers(): Observable<Any>
        fun onSearchFlights(): Observable<Any>
        fun onDateSelected(): Observable<Date>
    }
}
