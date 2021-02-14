package com.example.testapp.presentation.ribs.root.find_flights.available_flights.flight_summary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.entity.FlightSummaryVM
import kotlinx.android.synthetic.main.item_flight_summary.view.*

class FlightSummaryAdapter : RecyclerView.Adapter<FlightSummaryAdapter.FlightSummaryHolder>() {

    var summaries = arrayListOf<FlightSummaryVM>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightSummaryHolder {
        return FlightSummaryHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_flight_summary, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FlightSummaryHolder, position: Int) =
        holder.bindView(summaries[position])

    override fun getItemCount(): Int = summaries.count()

    inner class FlightSummaryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(flightSummary: FlightSummaryVM) {
            itemView.apply {
                flightSummary.apply {
                    vTitle.text = resources.getString(title)
                    vValue.text = value
                }
            }
        }
    }
}