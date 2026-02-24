package com.linkup.android.network.client

import android.util.Log
import com.linkup.android.data.datastore.UserRepository
import com.linkup.android.network.auth.refresh.RefreshRequest
import com.linkup.android.network.auth.refresh.RefreshService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val userRepository: UserRepository,
    private val refreshService: RefreshService
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {

        Log.d("TOKEN", "⚠️ 401 발생 → Authenticator 진입")

        if (responseCount(response) >= 2) return null

        val requestAccessToken =
            response.request.header("Authorization")?.removePrefix("Bearer ")

        return runBlocking {
            mutex.withLock {

                val currentAccessToken = userRepository.getCachedAccessToken()

                if (currentAccessToken != null &&
                    currentAccessToken != requestAccessToken
                ) {
                    return@withLock response.request.newBuilder()
                        .header("Authorization", "Bearer $currentAccessToken")
                        .build()
                }

                val refreshToken = userRepository.getCachedRefreshToken()
                    ?: return@withLock null

                Log.d("TOKEN", "🔄 Refresh 요청 시작")

                val refreshResponse = refreshService.refresh(
                    RefreshRequest(refreshToken)
                )

                Log.d("TOKEN", "✅ Refresh 응답: ${refreshResponse.code()}")

                if (refreshResponse.isSuccessful) {
                    val body = refreshResponse.body()?.data
                        ?: return@withLock null

                    userRepository.saveToken(
                        accessToken = body.accessToken,
                        refreshToken = body.refreshToken
                    )
                    Log.d("TOKEN", "💾 새 토큰 저장 완료")

                    return@withLock response.request.newBuilder()
                        .header("Authorization", "Bearer ${body.accessToken}")
                        .build()
                }

                userRepository.clearTokens()
                return@withLock null
            }
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}