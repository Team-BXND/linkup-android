package com.linkup.android.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.lang.reflect.Type

object ErrorParser {

    private val gson = Gson()

    fun <T> parse(response: Response<*>, clazz: Class<T>): T? {
        return try {
            val errorBody = response.errorBody()?.string()
            if (errorBody.isNullOrEmpty()) return null

            val type: Type = TypeToken
                .getParameterized(BaseResponse::class.java, clazz)
                .type

            val baseResponse: BaseResponse<T> =
                gson.fromJson(errorBody, type)

            baseResponse.data
        } catch (e: Exception) {
            null
        }
    }
}