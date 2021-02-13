package com.example.testapp.presentation.root.find_flights

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.testapp.R
import com.example.testapp.extensions.showErrorDialog
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.find_flights_rib.view.*
import java.util.Date
import java.util.Calendar

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
    private val dateSelectedPublisher = PublishRelay.create<Date>()

    override fun onFinishInflate() {
        super.onFinishInflate()

        setupRx()
    }

    override fun onDetachedFromWindow() {
        compositeDisposable.clear()
        super.onDetachedFromWindow()
    }

    override fun showError(error: Throwable) = showErrorDialog(error) {}

    override fun selectDate(chosenDate: Date) {
        val chosenCalendar = Calendar.getInstance().apply { time = chosenDate }
        val pickerDialog = DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth -> publishNewDate(year, monthOfYear, dayOfMonth) },
            chosenCalendar.get(Calendar.YEAR),
            chosenCalendar.get(Calendar.MONTH),
            chosenCalendar.get(Calendar.DAY_OF_MONTH))
        pickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis
        pickerDialog.show()
    }

    override fun updateOrigin(city: String, code: String) {
        vOrigin.text = city
        vOriginCode.text = code
    }

    override fun updateDestination(city: String, code: String) {
        vDestination.text = city
        vDestinationCode.text = code
    }

    override fun updateDateOut(date: String) {
        vDepartureDate.text = date
    }

    override fun updatePassengers(adults: Int, teens: Int, children: Int) {
        vPassengers.text = providePassengersPreview(adults, teens, children)
    }

    override fun progressVisibility(): BehaviorRelay<Boolean> = progressVisibilityPublisher

    override fun searchAbilityState(): BehaviorRelay<Boolean> = searchAbilityStatePublisher

    override fun onChooseOrigin(): Observable<Any> =
        RxView.clicks(vOrigin).mergeWith(RxView.clicks(vOriginCode))

    override fun onChooseDestination(): Observable<Any> =
        RxView.clicks(vDestination).mergeWith(RxView.clicks(vDestinationCode))

    override fun onChooseDate(): Observable<Any> = RxView.clicks(vDepartureDate)

    override fun onSelectPassengers(): Observable<Any> = RxView.clicks(vPassengers)

    override fun onSearchFlights(): Observable<Any> = RxView.clicks(vSearchFlights)

    override fun onDateSelected(): Observable<Date> = dateSelectedPublisher

    private fun setupRx() {
        compositeDisposable.addAll(
            progressVisibilityPublisher.subscribe {
                vProgress.visibility = if (it) VISIBLE else GONE
            },
            searchAbilityStatePublisher.subscribe { vSearchFlights.isEnabled = it }
        )
    }

    private fun publishNewDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        dateSelectedPublisher.accept(
            Calendar.getInstance().apply { set(year, monthOfYear, dayOfMonth) }.time
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
        @StringRes ageDescription: Int,
    ): String {
        if (years < 1) return ""

        val ageRange = resources.getString(ageDescription)
        val passengerType =
            resources.getString(if (years == 1) typeNameSingular else typeNamePlural)
        return "\n$years $passengerType ($ageRange)"
    }
}
