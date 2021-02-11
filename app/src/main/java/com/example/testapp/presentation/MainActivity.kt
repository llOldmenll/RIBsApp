package com.example.testapp.presentation

import android.os.Bundle
import android.view.ViewGroup
import com.example.testapp.R
import com.uber.rib.core.RibActivity
import com.uber.rib.core.ViewRouter

class MainActivity : RibActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun createRouter(parentViewGroup: ViewGroup?): ViewRouter<*, *, *> {
        TODO("Not yet implemented")
    }
}