package com.linkup.android.network

import com.linkup.android.BuildConfig

object LinkUpUrl {
    const val baseUrl = BuildConfig.BASE_URL

    object Auth {
        const val auth = "${baseUrl}/auth"

        const val signUp = "${auth}/signup"
        const val signIn = "${auth}/signin"

        const val send = "${auth}/pwchange/send"
        const val verify = "${auth}/pwchange/verify"
        const val change = "${auth}/pwchange/change"

        const val refresh = "${auth}/refresh"
    }
}