package com.linkup.android.feature.auth

object PasswordValidator {
    private val regex =
        Regex("""^(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*])[a-z\d!@#$%^&*]{8,}$""")

    fun isValidPassword(pw: String): Boolean {
        return regex.matches(pw)
    }
}