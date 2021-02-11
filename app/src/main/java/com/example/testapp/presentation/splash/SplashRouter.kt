package com.example.testapp.presentation.splash

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link SplashBuilder.SplashScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class SplashRouter(
    view: SplashView,
    interactor: SplashInteractor,
    component: SplashBuilder.Component) : ViewRouter<SplashView, SplashInteractor, SplashBuilder.Component>(view, interactor, component)
