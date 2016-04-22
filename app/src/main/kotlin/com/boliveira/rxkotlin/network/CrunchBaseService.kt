package com.boliveira.rxkotlin.network

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.logging.HttpLoggingInterceptor
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory


class CrunchBaseService {
    companion object {
        val baseEndpoint = "https://api.crunchbase.com/v/3/"

        private var _service: CrunchBaseApi? = null

        val builder: CrunchBaseApi
            get() {
                _service?.let {
                    return it
                }

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

                _service = retrofit.create(CrunchBaseApi::class.java)
                return _service!!
            }
    }
}




