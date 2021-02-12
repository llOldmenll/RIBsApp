package com.example.testapp.presentation.root

import com.example.testapp.presentation.splash.SplashBuilder
import com.uber.rib.core.Router
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link RootBuilder.RootScope}.
 */
class RootRouter(
    view: RootView,
    interactor: RootInteractor,
    component: RootBuilder.Component,
    private val splashBuilder: SplashBuilder
) : ViewRouter<RootView, RootInteractor, RootBuilder.Component>(view, interactor, component) {

    private var currentChild: Router<*, *>? = null

    fun attachSplashScreen() {
        currentChild = splashBuilder.build(view).let {
            attachChild(it)
            view.addView(it.view)
            it
        }
    }

    fun detachCurrentChild() {
        currentChild?.let {
            detachChild(it)
            if (it is ViewRouter<*, *, *>) view.removeView(it.view)
            currentChild = null
        }
    }
}
