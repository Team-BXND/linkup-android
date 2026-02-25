package com.linkup.android.network.rank

import com.linkup.android.network.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface RankService {
    @GET("ranking")
    suspend fun getRank(): Response<BaseResponse<List<RankResponse>>>
}
