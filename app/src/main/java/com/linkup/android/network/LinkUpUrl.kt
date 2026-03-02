package com.linkup.android.network

import com.linkup.android.BuildConfig

object LinkUpUrl {
    const val BASE_URL = BuildConfig.BASE_URL

    object Auth {
        const val AUTH = "${BASE_URL}auth"

        const val SIGN_UP = "${AUTH}/signup"
        const val SIGN_IN = "${AUTH}/signin"

        const val SEND = "${AUTH}/pwchange/send"
        const val VERIFY = "${AUTH}/pwchange/verify"
        const val CHANGE = "${AUTH}/pwchange/change"

        const val REFRESH = "${AUTH}/refresh"
    }

    object Popular {
        const val  POPULAR = "${BASE_URL}popular"
        const val HOT = "${POPULAR}/hot"
    }

    object Post {
        const val POST = "${BASE_URL}posts"

        const val DETAIL = "${POST}/{id}"

        const val ANSWER = "${DETAIL}/answer"

        const val LIKE =  "${DETAIL}/like"

        const val ACCEPT = "${DETAIL}/accept/{ansid}"
    }

    object Upload {
        const val UPLOAD = "${BASE_URL}upload"
    }
}