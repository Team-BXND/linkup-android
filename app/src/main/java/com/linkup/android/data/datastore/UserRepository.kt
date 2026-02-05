package com.linkup.android.data.datastore
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.linkup.android.data.datastore.UserPrefsKeys.ACCESS_TOKEN
import com.linkup.android.data.datastore.UserPrefsKeys.PUBLIC_ID
import com.linkup.android.data.datastore.UserPrefsKeys.REFRESH_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
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

    val publicIdFlow: Flow<String?> = context.dataStore.data
        .map { it[PUBLIC_ID] }

    suspend fun saveUserData(
        publicId: String? = null,
        accessToken: String? = null,
        refreshToken: String? = null
    ) {
        context.dataStore.edit { prefs ->
            publicId?.let { prefs[PUBLIC_ID] = it }
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

    suspend fun clearUserData() {
        context.dataStore.edit { prefs ->
            prefs.remove(PUBLIC_ID)
            prefs.remove(ACCESS_TOKEN)
            prefs.remove(REFRESH_TOKEN)
        }
        cachedAccessToken = null
        cachedRefreshToken = null
    }

    suspend fun getPublicIdSnapshot(): String? =
        context.dataStore.data.map { it[PUBLIC_ID] }.first()

    suspend fun getAccessTokenSnapshot(): String? =
        context.dataStore.data.map { it[ACCESS_TOKEN] }.first()

    suspend fun getRefreshTokenSnapshot(): String? =
        context.dataStore.data.map { it[REFRESH_TOKEN] }.first()
}
