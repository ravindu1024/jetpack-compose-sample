package com.ravindu1024.data

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val newsApiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        if (request.url.host == "newsapi.org") {
            builder.addHeader("X-Api-Key", newsApiKey)
        }

        return chain.proceed(builder.build())
    }
}