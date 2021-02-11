package com.example.testapp.presentation.splash

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Top level view for {@link SplashBuilder.SplashScope}.
 */
class SplashView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle), SplashInteractor.SplashPresenter
