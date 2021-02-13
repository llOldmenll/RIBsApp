package com.example.testapp.di

import com.example.data.network.OkHttpClientFactory
import com.example.data.network.OkHttpClientFactoryImpl
import com.example.data.network.ConverterFactory
import com.example.data.network.ConverterFactoryImpl
import com.example.data.network.CallAdapterFactory
import com.example.data.network.CallAdapterFactoryImpl
import com.example.data.network.NetworkServiceFactory
import com.example.data.network.NetworkServiceFactoryImpl
import com.example.testapp.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class NetworkModule {

    @Module
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

        @Provides
        @JvmStatic
        @Named("test")
        internal fun testNetworkServiceFactory(
            okHttpClientFactory: OkHttpClientFactory,
            converterFactory: ConverterFactory,
            callAdapterFactory: CallAdapterFactory,
        ): NetworkServiceFactory = NetworkServiceFactoryImpl(
            BuildConfig.BASE_TEST_URL,
            okHttpClientFactory,
            converterFactory,
            callAdapterFactory
        )

        @Provides
        @JvmStatic
        @Named("prod")
        internal fun mainNetworkServiceFactory(
            okHttpClientFactory: OkHttpClientFactory,
            converterFactory: ConverterFactory,
            callAdapterFactory: CallAdapterFactory,
        ): NetworkServiceFactory = NetworkServiceFactoryImpl(
            BuildConfig.BASE_TEST_URL,
            okHttpClientFactory,
            converterFactory,
            callAdapterFactory
        )
    }
}