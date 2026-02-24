package com.linkup.android.data.datastore
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.linkup.android.data.datastore.UserPrefsKeys.ACCESS_TOKEN
import com.linkup.android.data.datastore.UserPrefsKeys.REFRESH_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class UserRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var cachedAccessToken: String? = null
    private var cachedRefreshToken: String? = null

    suspend fun loadAccessToken() {
        cachedAccessToken = getAccessTokenSnapshot()
        cachedRefreshToken = getRefreshTokenSnapshot()
    }

    fun getCachedAccessToken(): String? = cachedAccessToken
    fun getCachedRefreshToken(): String? = cachedRefreshToken

    suspend fun saveToken(
        accessToken: String? = null,
        refreshToken: String? = null
    ) {
        context.dataStore.edit { prefs ->
            accessToken?.let {
                prefs[ACCESS_TOKEN] = it
                cachedAccessToken = it
            }
            refreshToken?.let {
                prefs[REFRESH_TOKEN] = it
                cachedRefreshToken = it
            }
        }
    }

    suspend fun clearTokens() {
        context.dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN)
            prefs.remove(REFRESH_TOKEN)
        }
        cachedAccessToken = null
        cachedRefreshToken = null
    }


    suspend fun getAccessTokenSnapshot(): String? =
        context.dataStore.data.map { it[ACCESS_TOKEN] }.first()

    suspend fun getRefreshTokenSnapshot(): String? =
        context.dataStore.data.map { it[REFRESH_TOKEN] }.first()
}
