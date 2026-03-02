package com.linkup.android.network.client

import android.util.Log
import com.linkup.android.data.datastore.UserRepository
import com.linkup.android.network.LinkUpUrl
import com.linkup.android.network.auth.refresh.RefreshRequest
import com.linkup.android.network.auth.refresh.RefreshService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userRepository: UserRepository,
    private val refreshService: RefreshService
) : Interceptor {

    private val mutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val accessToken = userRepository.getCachedAccessToken()
        val authRequest = if (!accessToken.isNullOrEmpty()) {
            request.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
        } else request

        var response = chain.proceed(authRequest)

        if (response.code == 403) {
            response.close()

            return runBlocking {
                mutex.withLock {

                    val refreshToken = userRepository.getCachedRefreshToken()
                        ?: return@withLock response

                    val refreshResponse =
                        refreshService.refresh(RefreshRequest(refreshToken))

                    if (!refreshResponse.isSuccessful) {
                        userRepository.clearTokens()
                        return@withLock response
                    }

                    val body = refreshResponse.body()?.data
                        ?: return@withLock response

                    userRepository.saveToken(
                        body.accessToken,
                        body.refreshToken
                    )

                    val newRequest = request.newBuilder()
                        .header("Authorization", "Bearer ${body.accessToken}")
                        .build()

                    chain.proceed(newRequest)
                }
            }
        }

        return response
    }
}