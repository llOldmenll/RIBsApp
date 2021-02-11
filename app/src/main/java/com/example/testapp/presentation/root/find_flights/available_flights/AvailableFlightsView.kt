package com.example.testapp.presentation.root.find_flights.available_flights

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Top level view for {@link AvailableFlightsBuilder.AvailableFlightsScope}.
 */
class AvailableFlightsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle), AvailableFlightsInteractor.AvailableFlightsPresenter
