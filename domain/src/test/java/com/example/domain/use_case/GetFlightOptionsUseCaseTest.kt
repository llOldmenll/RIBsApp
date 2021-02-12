package com.example.domain.use_case

import com.example.domain.entity.flight.FlightOptions
import com.example.domain.entity.request.GetAvailableFlightsRequest
import com.example.domain.repository.FlightRepository
import com.nhaarman.mockitokotlin2.any
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class GetFlightOptionsUseCaseTest {

    @Mock
    lateinit var flightRepository: FlightRepository

    lateinit var getFlightOptionsUseCase: GetFlightOptionsUseCase

    private val fakeFlightOptions by lazy { FlightOptions(listOf()) }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        getFlightOptionsUseCase = GetFlightOptionsUseCase(flightRepository)

        `when`(flightRepository.getAvailableFlights(any()))
            .thenReturn(Single.just(fakeFlightOptions))
    }

    @Test
    fun getAvailableFlightsWhenUseCaseExecuted() {
        // Arrange
        val request = GetAvailableFlightsRequest("origin", "destination", "", 1, 0, 0)

        // Act
        val result = getFlightOptionsUseCase.execute(request)

        // Assert
        verify(flightRepository).getAvailableFlights(request)
        result.test()
            .assertNoErrors()
            .assertValue { it == fakeFlightOptions }
            .dispose()
    }
}