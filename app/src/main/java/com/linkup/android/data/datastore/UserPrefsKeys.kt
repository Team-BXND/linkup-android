package com.linkup.android.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object UserPrefsKeys {
    val PUBLIC_ID = stringPreferencesKey("public_id")
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
}