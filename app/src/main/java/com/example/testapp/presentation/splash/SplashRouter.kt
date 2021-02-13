package com.example.testapp.presentation.splash

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link SplashBuilder.SplashScope}.
 */
class SplashRouter(
    view: SplashView,
    interactor: SplashInteractor,
    component: SplashBuilder.Component
) : ViewRouter<SplashView, SplashInteractor, SplashBuilder.Component>(view, interactor, component)
