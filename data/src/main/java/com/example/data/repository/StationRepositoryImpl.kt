package com.example.data.repository

import com.example.data.entity.station.StationsEntity
import com.example.data.network.service.StationNetworkService
import com.example.domain.entity.station.Stations
import com.example.domain.mapper.Mapper
import com.example.domain.repository.StationRepository
import io.reactivex.Single

class StationRepositoryImpl(
    private val stationNetworkService: StationNetworkService,
    private val stationsMapper: Mapper<StationsEntity, Stations>
) : StationRepository {
    override fun getStations(): Single<Stations> =
        stationNetworkService.getStations().map { stationsMapper.map(it) }
}