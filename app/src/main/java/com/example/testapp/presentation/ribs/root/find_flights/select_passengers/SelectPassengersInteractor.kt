package com.example.testapp.presentation.ribs.root.find_flights.select_passengers

import com.example.domain.entity.request.PassengersRequest
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Coordinates Business Logic for [SelectPassengersScope].
 */
@RibInteractor
class SelectPassengersInteractor :
    Interactor<SelectPassengersInteractor.SelectPassengersPresenter, SelectPassengersRouter>() {

    @Inject
    lateinit var presenter: SelectPassengersPresenter
    @Inject
    lateinit var passengers: PassengersRequest
    @Inject
    lateinit var ribListener: Listener

    private val compositeDisposable = CompositeDisposable()

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        updateCounters()
        setupRx()
    }

    override fun willResignActive() {
        compositeDisposable.clear()
        super.willResignActive()
    }

    override fun handleBackPress(): Boolean {
        ribListener.onClose()
        return true
    }

    private fun updateCounters() {
        presenter.updateAdultsCount(passengers.adults)
        presenter.updateTeensCount(passengers.teens)
        presenter.updateChildrenCount(passengers.children)
    }

    private fun setupRx() {
        compositeDisposable.addAll(
            presenter.onClose().subscribe { handleBackPress() },
            presenter.onAdultsCountChanged().subscribe { passengers.adults = it },
            presenter.onTeenCountChanged().subscribe { passengers.teens = it },
            presenter.onChildrenCountChanged().subscribe { passengers.children = it },
            presenter.onConfirm().subscribe {
                ribListener.onPassengersSelected(passengers)
                handleBackPress()
            }
        )
    }

    interface Listener {
        fun onClose()
        fun onPassengersSelected(passengers: PassengersRequest)
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface SelectPassengersPresenter {
        fun setMaxPassengersCount(max: Int)
        fun updateAdultsCount(number: Int)
        fun updateTeensCount(number: Int)
        fun updateChildrenCount(number: Int)

        fun onClose(): Observable<Unit>
        fun onConfirm(): Observable<Any>
        fun onAdultsCountChanged(): Observable<Int>
        fun onTeenCountChanged(): Observable<Int>
        fun onChildrenCountChanged(): Observable<Int>
    }
}
