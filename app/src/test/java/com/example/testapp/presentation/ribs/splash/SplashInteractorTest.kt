package com.example.testapp.presentation.ribs.splash

import com.example.domain.entity.station.Stations
import com.example.domain.exception.NullableDataException
import com.example.domain.use_case.GetStationsUseCase
import com.example.testapp.helper.RxImmediateSchedulerRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper
import io.reactivex.Single

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SplashInteractorTest : RibTestBasePlaceholder() {

    @Mock
    internal lateinit var presenter: SplashInteractor.SplashPresenter
    @Mock
    internal lateinit var router: SplashRouter
    @Mock
    internal lateinit var getStationsUseCase: GetStationsUseCase
    @Mock
    internal lateinit var ribListener: SplashInteractor.Listener
    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

    private val fakeStations by lazy { Stations(listOf()) }
    private var interactor: SplashInteractor? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        interactor = TestSplashInteractor.create(
            presenter,
            getStationsUseCase,
            ribListener
        )

        `when`(getStationsUseCase.execute()).thenReturn(Single.just(fakeStations))
    }

    @Test
    fun loadStationsWhenDidBecomeActive() {
        // Act
        InteractorHelper.attach(interactor, presenter, router, null)

        // Assert
        verify(getStationsUseCase).execute()
    }

    @Test
    fun notifyParentRibWhenStationsLoadedSuccessfully() {
        // Act
        InteractorHelper.attach(interactor, presenter, router, null)

        // Assert
        verify(ribListener).onStationsLoaded(fakeStations)
    }

    @Test
    fun showErrorDialogWhenStationsLoadingFailed() {
        // Arrange
        val error = NullableDataException()
        `when`(getStationsUseCase.execute()).thenReturn(Single.error(error))

        // Act
        InteractorHelper.attach(interactor, presenter, router, null)

        // Assert
        verify(presenter).showError(eq(error), any())
    }
}