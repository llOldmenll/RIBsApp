package com.example.data.network

import com.example.data.network.adapter.NullToEmptyStringAdapter
import com.squareup.moshi.Moshi
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory

class ConverterFactoryImpl : ConverterFactory {

    private val moshi by lazy {
        Moshi.Builder()
            .add(NullToEmptyStringAdapter())
            .build()
    }

    override fun create(): Converter.Factory = MoshiConverterFactory.create(moshi)
}