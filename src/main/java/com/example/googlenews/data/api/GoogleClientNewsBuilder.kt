package com.example.googlenews.data.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GoogleClientNewsBuilder {

    private val BASE_URL = "http://newsapi.org/"
    private val APP_KEY = "a2b13e724b6443379d242e40c552f084"

    fun buildRetrofit(): GoogleClientService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GoogleClientService::class.java)


    private val requestInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("apiKey", APP_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .callTimeout(20, TimeUnit.SECONDS)
        .build()
}