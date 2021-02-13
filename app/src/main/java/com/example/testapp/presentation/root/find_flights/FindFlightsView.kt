package com.example.testapp.presentation.root.find_flights

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.testapp.R
import com.example.testapp.extensions.showErrorDialog
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.find_flights_rib.view.*
import java.lang.StringBuilder

/**
 * Top level view for {@link FindFlightsBuilder.FindFlightsScope}.
 */
class FindFlightsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle), FindFlightsInteractor.FindFlightsPresenter {

    private val compositeDisposable = CompositeDisposable()
    private val progressVisibilityPublisher = BehaviorRelay.createDefault(false)
    private val searchAbilityStatePublisher = BehaviorRelay.createDefault(false)

    override fun onFinishInflate() {
        super.onFinishInflate()

        setupRx()
    }

    override fun onDetachedFromWindow() {
        compositeDisposable.clear()
        super.onDetachedFromWindow()
    }

    override fun showError(error: Throwable) = showErrorDialog(error) {}

    override fun updateOrigin(origin: String) {
        vOrigin.text = origin
    }

    override fun updateDestination(destination: String) {
        vDestination.text = destination
    }

    override fun updateDateOut(date: String) {
        vDepartureDate.text = date
    }

    override fun updatePassengers(adults: Int, teens: Int, children: Int) {
        vPassengers.text = providePassengersPreview(adults, teens, children)
    }

    override fun progressVisibility(): BehaviorRelay<Boolean> = progressVisibilityPublisher

    override fun searchAbilityState(): BehaviorRelay<Boolean> = searchAbilityStatePublisher

    override fun onChooseOrigin(): Observable<Any> = RxView.clicks(vOrigin)

    override fun onChooseDestination(): Observable<Any> = RxView.clicks(vDestination)

    override fun onChooseDate(): Observable<Any> = RxView.clicks(vDepartureDate)

    override fun onSelectPassengers(): Observable<Any> = RxView.clicks(vPassengers)

    override fun onSearchFlights(): Observable<Any> = RxView.clicks(vSearchFlights)

    private fun setupRx() {
        compositeDisposable.addAll(
            progressVisibilityPublisher.subscribe {
                vProgress.visibility = if (it) VISIBLE else GONE
            },
            searchAbilityStatePublisher.subscribe { vSearchFlights.isEnabled = it }
        )
    }

    private fun providePassengersPreview(adults: Int, teens: Int, children: Int): String {
        return StringBuilder("").apply {
            append(
                providePassengersPreviewForType(
                    adults, R.string.adult, R.string.adults, R.string.adult_age
                )
            )
            append(
                providePassengersPreviewForType(
                    teens, R.string.teen, R.string.teens, R.string.teen_age
                )
            )
            append(
                providePassengersPreviewForType(
                    children, R.string.child, R.string.children, R.string.child_age
                )
            )
        }.toString().trim()
    }

    private fun providePassengersPreviewForType(
        years: Int,
        @StringRes typeNameSingular: Int,
        @StringRes typeNamePlural: Int,
        @StringRes ageDescription: Int
    ): String {
        if (years < 1) return ""

        val ageRange = resources.getString(ageDescription)
        val passengerType =
            resources.getString(if (years == 1) typeNameSingular else typeNamePlural)
        return "\n$years $passengerType ($ageRange)"
    }
}
