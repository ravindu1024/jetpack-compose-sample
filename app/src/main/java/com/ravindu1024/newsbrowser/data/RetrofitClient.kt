package com.ravindu1024.newsbrowser.data

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {
        fun init(
            enableLogging: Boolean,
            baseUrl: String,
            gson: Gson,
            headerInterceptor: Interceptor
        ): Retrofit {

            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            val okHttpBuilder = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)

            if (enableLogging) {
                okHttpBuilder.addInterceptor(logger)
            }

            val client = okHttpBuilder.build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}