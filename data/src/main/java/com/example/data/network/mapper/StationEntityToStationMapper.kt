package com.example.data.network.mapper

import com.example.data.entity.station.StationEntity
import com.example.domain.entity.station.Station
import com.example.domain.entity.station.StationDescription
import com.example.domain.mapper.Mapper
import com.example.domain.utils.toAlphaNumeric
import com.example.domain.utils.toLowerCaseDefault

class StationEntityToStationMapper : Mapper<StationEntity, Station> {

    override fun map(from: StationEntity): Station {
        val stationDescription = StationDescription(
            from.code ?: "",
            from.name ?: "",
            from.countryName ?: "",
            from.timeZoneCode ?: ""
        )

        return Station(
            provideKeyWords(from.alias, stationDescription),
            stationDescription
        )
    }

    private fun provideKeyWords(
        alias: List<String>?,
        stationDescription: StationDescription
    ): Set<String> {
        return mutableSetOf<String>().apply {
            addAll(alias ?: listOf())
            add(stationDescription.code.toLowerCaseDefault())
            add(stationDescription.name.toLowerCaseDefault().toAlphaNumeric())
            add(stationDescription.countryName.toLowerCaseDefault())
        }
    }
}