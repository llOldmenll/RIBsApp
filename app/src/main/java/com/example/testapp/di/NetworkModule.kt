package com.example.testapp.di

import com.example.data.network.OkHttpClientFactory
import com.example.data.network.OkHttpClientFactoryImpl
import com.example.data.network.ConverterFactory
import com.example.data.network.ConverterFactoryImpl
import com.example.data.network.CallAdapterFactory
import com.example.data.network.CallAdapterFactoryImpl
import dagger.Provides

@dagger.Module
abstract class NetworkModule {

    @dagger.Module
    companion object {

        @Provides
        @JvmStatic
        internal fun interceptorsFactory(): OkHttpClientFactory = OkHttpClientFactoryImpl()

        @Provides
        @JvmStatic
        internal fun converterFactory(): ConverterFactory = ConverterFactoryImpl()

        @Provides
        @JvmStatic
        internal fun callAdapterFactory(): CallAdapterFactory = CallAdapterFactoryImpl()
    }
}