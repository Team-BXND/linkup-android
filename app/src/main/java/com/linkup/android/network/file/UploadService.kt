package com.linkup.android.network.file

import com.linkup.android.network.BaseResponse
import com.linkup.android.network.LinkUpUrl
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


interface UploadService {
    @Multipart
    @POST(LinkUpUrl.Upload.UPLOAD)
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    ): Response<UploadResponse>

    @GET(LinkUpUrl.Upload.UPLOAD)
    suspend fun getTempUrl(
        @Query("s3Key") s3key: String
    ): Response<BaseResponse<String>>
}