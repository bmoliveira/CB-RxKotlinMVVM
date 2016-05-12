package com.boliveira.crunchbase.network

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.logging.HttpLoggingInterceptor
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory

// Singleton to represent CrunchBase API with interceptors
object CrunchBaseService {
    val builder: CrunchBaseApi

    val companyEndpointPrefix: String
        get() = "https://www.crunchbase.com"

    private val baseEndpoint: String
        get() = "https://api.crunchbase.com/v/3/"

    init {
        var logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        var client = OkHttpClient()
        client.interceptors().add(logging)

        var retrofit = Retrofit.Builder()
                .baseUrl(baseEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build()

        builder = retrofit.create(CrunchBaseApi::class.java)
    }
}





