package com.example.testapp.presentation.ribs.root.find_flights.available_flights

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.entity.FlightOptionVM
import com.example.testapp.presentation.ribs.root.find_flights.available_flights.adapter.AvailableFlightsAdapter
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.available_flights_rib.view.*
import kotlinx.android.synthetic.main.select_airport_rib.view.vRecycler
import kotlinx.android.synthetic.main.select_airport_rib.view.vToolbar

/**
 * Top level view for {@link AvailableFlightsBuilder.AvailableFlightsScope}.
 */
class AvailableFlightsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attrs, defStyle),
    AvailableFlightsInteractor.AvailableFlightsPresenter {

    private val onClosePublisher = PublishRelay.create<Unit>()
    private val onItemSelectedPublisher = PublishRelay.create<FlightOptionVM>()
    private val availableFlightsAdapter = AvailableFlightsAdapter(onItemSelectedPublisher)

    override fun onFinishInflate() {
        super.onFinishInflate()

        initUI()
    }

    private fun initUI() {
        vToolbar.setNavigationOnClickListener { onClosePublisher.accept(Unit) }
        vRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = availableFlightsAdapter
        }
    }

    override fun updateFlightOptions(flightOptions: List<FlightOptionVM>) {
        availableFlightsAdapter.flightOptions = ArrayList(flightOptions)
        vFlightsNotFound.visibility = if(flightOptions.isEmpty()) VISIBLE else GONE
    }

    override fun onClose(): Observable<Unit> = onClosePublisher

    override fun onFlightOptionSelected(): Observable<FlightOptionVM> = onItemSelectedPublisher
}
