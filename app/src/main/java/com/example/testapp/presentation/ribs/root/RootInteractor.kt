package com.example.testapp.presentation.ribs.root

import com.example.domain.entity.station.Stations
import com.example.testapp.presentation.ribs.splash.SplashInteractor
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [RootScope].
 */
@RibInteractor
class RootInteractor : Interactor<RootInteractor.RootPresenter, RootRouter>() {

    @Inject
    lateinit var presenter: RootPresenter

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        router.attachSplashScreen()
    }

    override fun willResignActive() {
        super.willResignActive()

        // TODO: Perform any required clean up here, or delete this method entirely if not needed.
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface RootPresenter

    inner class SplashListener : SplashInteractor.Listener {
        override fun onStationsLoaded(stations: Stations) {
            router.detachCurrentChild()
            router.attachFindFlightsScreen(stations)
        }
    }
}
