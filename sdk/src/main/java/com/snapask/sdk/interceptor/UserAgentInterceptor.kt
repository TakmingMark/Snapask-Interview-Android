package com.snapask.sdk.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor(private val userAgent: String) : Interceptor {

    companion object {
        private const val USER_AGENT = "User-Agent"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().header(USER_AGENT, userAgent).build()

        return chain.proceed(request)
    }
}