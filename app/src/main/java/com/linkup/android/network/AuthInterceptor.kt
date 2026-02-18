package com.linkup.android.network

import com.linkup.android.data.datastore.UserRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userRepository: UserRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        val noAuthPaths = listOf(
            "/auth/login",
            "/auth/signup",
            "/auth/refresh"
        )

        if (noAuthPaths.any { path.contains(it) }) {
            return chain.proceed(request)
        }

        val accessToken = userRepository.getCachedAccessToken()

        // 토큰 없으면 그냥 원본 요청
        if (accessToken.isNullOrEmpty()) {
            return chain.proceed(request)
        }

        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(newRequest)
    }
}