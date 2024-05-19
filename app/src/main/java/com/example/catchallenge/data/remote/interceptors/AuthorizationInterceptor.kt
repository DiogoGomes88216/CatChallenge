package com.example.catchallenge.data.remote.interceptors

import com.example.catchallenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(API_KEY_HEADER, BuildConfig.API_KEY)

        return chain.proceed(requestBuilder.build())
    }

    companion object{
        const val API_KEY_HEADER = "x-api-key"
    }

}