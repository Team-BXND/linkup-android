package com.linkup.android.network

import com.linkup.android.BuildConfig

object LinkUpUrl {
    const val baseUrl = BuildConfig.BASE_URL

    const val signUp = "${baseUrl}auth/signup"
}