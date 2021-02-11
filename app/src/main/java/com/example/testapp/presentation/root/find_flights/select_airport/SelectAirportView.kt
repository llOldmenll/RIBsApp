package com.example.testapp.presentation.root.find_flights.select_airport

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Top level view for {@link SelectAirportBuilder.SelectAirportScope}.
 */
class SelectAirportView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle), SelectAirportInteractor.SelectAirportPresenter
