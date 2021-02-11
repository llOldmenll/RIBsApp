package com.example.testapp.presentation.root.find_flights.select_passengers

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Top level view for {@link SelectPassengersBuilder.SelectPassengersScope}.
 */
class SelectPassengersView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle), SelectPassengersInteractor.SelectPassengersPresenter
