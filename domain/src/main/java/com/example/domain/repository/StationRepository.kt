package com.example.domain.repository

import com.example.domain.entity.station.Stations
import io.reactivex.Single

/**
 * Used for interaction with stations data
 */
interface StationRepository {
    /**
     * Get all stations
     *
     * @return Stations
     */
    fun getStations(): Single<Stations>
}