package com.example.testapp.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.testapp.R
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.counter_layout.view.*

class Counter(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    var minValue = 0
    var maxValue = Int.MAX_VALUE
    var value = 0
        set(value) {
            field = value
            onValueChangedPublisher.accept(field)
            vValue.text = if(field == 0) "" else field.toString()
        }

    private val onValueChangedPublisher = PublishRelay.create<Int>()

    init {
        LayoutInflater.from(context).inflate(R.layout.counter_layout, this)
        orientation = HORIZONTAL

        vValue.text = value.toString()
        vPlus.setOnClickListener { if (value < maxValue) ++value }
        vMinus.setOnClickListener { if (value > minValue) --value }
    }

    fun onValueChanged(): Observable<Int> = onValueChangedPublisher
}