package com.example.domain.use_case

import com.example.domain.entity.station.Stations
import com.example.domain.repository.StationRepository
import com.example.domain.use_case.base.SingleUseCase
import io.reactivex.Single

class GetStationsUseCase(
    private val stationsRepository: StationRepository
) : SingleUseCase<Unit, Stations> {
    override fun execute(data: Unit?): Single<Stations> = stationsRepository.getStations()
}