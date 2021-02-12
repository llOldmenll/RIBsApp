package com.example.data.network.mapper

import com.example.data.entity.station.StationEntity
import com.example.data.entity.station.StationsEntity
import com.example.domain.entity.station.Station
import com.example.domain.entity.station.Stations
import com.example.domain.mapper.Mapper

class StationsEntityToStationsMapper(
    private val stationMapper: Mapper<StationEntity, Station>
): Mapper<StationsEntity, Stations> {

    override fun map(from: StationsEntity): Stations = Stations(
        from.stations?.map { stationMapper.map(it) } ?: listOf()
    )
}