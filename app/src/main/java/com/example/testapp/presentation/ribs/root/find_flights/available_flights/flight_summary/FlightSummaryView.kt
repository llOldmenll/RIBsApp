package com.example.testapp.presentation.ribs.root.find_flights.available_flights.flight_summary

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Top level view for {@link FlightSummaryBuilder.FlightSummaryScope}.
 */
class FlightSummaryView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle), FlightSummaryInteractor.FlightSummaryPresenter
