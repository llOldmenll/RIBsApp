package com.example.testapp.presentation.root.find_flights.select_date

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Top level view for {@link SelectDateBuilder.SelectDateScope}.
 */
class SelectDateView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle), SelectDateInteractor.SelectDatePresenter
