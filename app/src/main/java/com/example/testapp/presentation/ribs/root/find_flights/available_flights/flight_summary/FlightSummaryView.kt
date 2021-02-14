package com.example.testapp.presentation.ribs.root.find_flights.available_flights.flight_summary

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.entity.FlightSummaryVM
import com.example.testapp.presentation.ribs.root.find_flights.available_flights.flight_summary.adapter.FlightSummaryAdapter
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.select_airport_rib.view.vRecycler
import kotlinx.android.synthetic.main.select_airport_rib.view.vToolbar

/**
 * Top level view for {@link FlightSummaryBuilder.FlightSummaryScope}.
 */
class FlightSummaryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attrs, defStyle), FlightSummaryInteractor.FlightSummaryPresenter {

    private val onClosePublisher = PublishRelay.create<Unit>()
    private val flightSummaryAdapter = FlightSummaryAdapter()

    override fun onFinishInflate() {
        super.onFinishInflate()

        initUI()
    }

    private fun initUI() {
        vToolbar.setNavigationOnClickListener { onClosePublisher.accept(Unit) }
        vRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = flightSummaryAdapter
        }
    }

    override fun updateFlightSummary(summaries: List<FlightSummaryVM>) {
        flightSummaryAdapter.summaries = ArrayList(summaries)
    }

    override fun onClose(): Observable<Unit> = onClosePublisher
}

