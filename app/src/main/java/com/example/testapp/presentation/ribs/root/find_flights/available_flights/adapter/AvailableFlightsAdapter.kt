package com.example.testapp.presentation.ribs.root.find_flights.available_flights.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.entity.FlightOptionVM
import com.jakewharton.rxrelay2.PublishRelay
import kotlinx.android.synthetic.main.item_available_flight.view.*

class AvailableFlightsAdapter(private val onItemSelected: PublishRelay<FlightOptionVM>) :
    RecyclerView.Adapter<AvailableFlightsAdapter.AvailableFlightsHolder>() {
    var flightOptions = arrayListOf<FlightOptionVM>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailableFlightsHolder {
        return AvailableFlightsHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_available_flight, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AvailableFlightsHolder, position: Int) =
        holder.bindView(flightOptions[position])

    override fun getItemCount(): Int = flightOptions.count()

    inner class AvailableFlightsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(flightOption: FlightOptionVM) {
            itemView.apply {
                flightOption.apply {
                    vDateOut.text = dateOut
                    vTimeOut.text = timeOut
                    vTimeIn.text = timeIn
                    vDuration.text = duration
                    vFlightNumber.text = flightNumber
                    vPrice.text = price
                }
                setOnClickListener { onItemSelected.accept(flightOption) }
            }
        }
    }
}