package com.example.data.network

import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

class NetworkServiceFactoryImpl(
    private val baseUrl: String,
    private val okHttpClient: OkHttpClient,
    private val converterFactory: Converter.Factory,
    private val callAdapterFactory: CallAdapter.Factory
) : NetworkServiceFactory {

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()
    }

    override fun <T> create(serviceType: Class<out T>): T = retrofit.create(serviceType)
}