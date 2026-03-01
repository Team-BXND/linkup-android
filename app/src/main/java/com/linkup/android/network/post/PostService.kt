package com.linkup.android.network.post

import com.linkup.android.network.BaseResponse
import com.linkup.android.network.LinkUpUrl
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {
    @GET(LinkUpUrl.Post.DETAIL)
    suspend fun getPostDetail(
        @Path("id") id: Int
    ): PostDetailResponse

    @POST(LinkUpUrl.Post.ANSWER)
    suspend fun writeComment(
        @Body request: AnswerRequest,
        @Path("id") id: Int
    ): Response<BaseResponse<PostResponse>>

    @POST(LinkUpUrl.Post.POST)
    suspend fun createPost(
        @Body request: PostRequest
    ): Response<BaseResponse<PostResponse>>

    @POST(LinkUpUrl.Post.LIKE)
    suspend fun like(
        @Path("id") id: Int
    ): Response<BaseResponse<PostResponse>>

    @PATCH(LinkUpUrl.Post.DETAIL)
    suspend fun updatePost (
        @Path("id") id: Int,
        @Body request: PatchRequest
    ): Response<BaseResponse<PostResponse>>

    @POST(LinkUpUrl.Post.ACCEPT)
    suspend fun acceptComment (
        @Path("id") id: Int,
        @Path("ansid") ansid: Int,
        @Body request: AcceptRequest
    ): Response<BaseResponse<PostResponse>>

    @DELETE(LinkUpUrl.Post.DETAIL)
    suspend fun deletePost(
        @Path("id") postId: Int
    ): Response<BaseResponse<PostResponse>>

    @DELETE(LinkUpUrl.Post.ANSWER)
    suspend fun deleteComment(
        @Path("id") id: Int
    ): Response<BaseResponse<PostResponse>>
}