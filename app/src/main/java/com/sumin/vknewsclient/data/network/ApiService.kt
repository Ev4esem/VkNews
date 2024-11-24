package com.sumin.vknewsclient.data.network

import com.sumin.vknewsclient.data.network.model.CommentsResponseDto
import com.sumin.vknewsclient.data.network.model.LikesCountResponse
import com.sumin.vknewsclient.data.network.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadNewsFeedResponse(
        @Query("access_token") token: String,
        @Query("count") count: Int,
    ): NewsFeedResponseDto

    @GET("wall.getComments?v=5.131&extended=1&fields=photo_100")
    suspend fun getCommentsByPost(
        @Query("access_token") token : String,
        @Query("owner_id") ownerId : Long,
        @Query("post_id") postId : Long,
    ): CommentsResponseDto

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadNewsFeedResponse(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String?,
        @Query("count") count: Int,
    ): NewsFeedResponseDto

    @GET("likes.add?v=5.131&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long,
    ): LikesCountResponse

    @GET("likes.delete?v=5.131&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponse

}
