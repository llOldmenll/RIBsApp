package com.example.data.network.service

import com.example.data.entity.station.StationsEntity
import io.reactivex.Single
import retrofit2.http.GET

interface StationNetworkService {
    @GET("static/stations.json")
    fun getStations(): Single<StationsEntity>
}