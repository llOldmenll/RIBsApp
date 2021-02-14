package com.example.testapp.presentation.ribs.root.find_flights.select_passengers

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.select_passengers_rib.view.*
import kotlinx.android.synthetic.main.select_passengers_rib.view.vToolbar

/**
 * Top level view for {@link SelectPassengersBuilder.SelectPassengersScope}.
 */
class SelectPassengersView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attrs, defStyle),
    SelectPassengersInteractor.SelectPassengersPresenter {

    companion object {
        private const val MIN_ADULTS = 1
    }

    private val onClosePublisher = PublishRelay.create<Unit>()

    override fun onFinishInflate() {
        super.onFinishInflate()

        initUI()
    }

    override fun setMaxPassengersCount(max: Int) {
        vAdultsCount.maxValue = max
        vTeensCount.maxValue = max
        vChildrenCount.maxValue = max
    }

    override fun updateAdultsCount(number: Int) {
        vAdultsCount.value = number
    }

    override fun updateTeensCount(number: Int) {
        vTeensCount.value = number
    }

    override fun updateChildrenCount(number: Int) {
        vChildrenCount.value = number
    }

    override fun onClose(): Observable<Unit> = onClosePublisher

    override fun onConfirm(): Observable<Any> = RxView.clicks(vConfirm)

    override fun onAdultsCountChanged(): Observable<Int> = vAdultsCount.onValueChanged()

    override fun onTeenCountChanged(): Observable<Int> = vTeensCount.onValueChanged()

    override fun onChildrenCountChanged(): Observable<Int> = vChildrenCount.onValueChanged()

    private fun initUI() {
        vAdultsCount.minValue = MIN_ADULTS
        vToolbar.setNavigationOnClickListener { onClosePublisher.accept(Unit) }
    }
}
