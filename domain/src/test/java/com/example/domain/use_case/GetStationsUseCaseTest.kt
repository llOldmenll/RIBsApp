package com.example.domain.use_case

import com.example.domain.entity.station.Stations
import com.example.domain.repository.StationRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class GetStationsUseCaseTest {

    @Mock
    lateinit var stationRepository: StationRepository

    lateinit var getStationsUseCase: GetStationsUseCase

    private val fakeStations by lazy { Stations(listOf()) }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        getStationsUseCase = GetStationsUseCase(stationRepository)

        `when`(stationRepository.getStations()).thenReturn(Single.just(fakeStations))
    }

    @Test
    fun getStationsWhenUseCaseExecuted() {
        // Act
        val result = getStationsUseCase.execute()

        // Assert
        verify(stationRepository).getStations()
        result.test()
            .assertNoErrors()
            .assertValue { it == fakeStations }
            .dispose()
    }
}