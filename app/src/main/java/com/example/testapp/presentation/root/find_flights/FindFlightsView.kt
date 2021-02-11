package com.example.testapp.presentation.root.find_flights

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Top level view for {@link FindFlightsBuilder.FindFlightsScope}.
 */
class FindFlightsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle), FindFlightsInteractor.FindFlightsPresenter
