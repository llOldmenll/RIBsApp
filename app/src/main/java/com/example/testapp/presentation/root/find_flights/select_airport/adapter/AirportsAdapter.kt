package com.example.testapp.presentation.root.find_flights.select_airport.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.station.Station
import com.example.domain.entity.station.StationDescription
import com.example.testapp.R
import com.jakewharton.rxrelay2.PublishRelay
import kotlinx.android.synthetic.main.item_airport.view.*

class AirportsAdapter(private val onItemSelected: PublishRelay<StationDescription>) :
    RecyclerView.Adapter<AirportsAdapter.AirportsHolder>() {
    var stations = arrayListOf<Station>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportsHolder {
        return AirportsHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_airport, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AirportsHolder, position: Int) =
        holder.bindView(stations[position].stationDescription)

    override fun getItemCount(): Int = stations.count()

    inner class AirportsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(stationDescription: StationDescription) {
            itemView.apply {
                stationDescription.apply {
                    vCity.text = name
                    vCountry.text = countryName
                    vCode.text = code
                    setOnClickListener { onItemSelected.accept(this) }
                }
            }
        }
    }
}