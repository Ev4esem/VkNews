package com.sumin.vknewsclient.data.repository

import android.app.Application
import com.sumin.vknewsclient.data.mapper.mapResponseToPosts
import com.sumin.vknewsclient.data.network.ApiFactory
import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.domain.model.StatisticItem
import com.sumin.vknewsclient.domain.model.StatisticType
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class NewsFeedRepositoryImpl(private val application: Application) : NewsFeedRepository {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)?.accessToken
    private val apiService = ApiFactory.apiService

    private val feedPosts = mutableListOf<FeedPost>()

    override suspend fun loadRecommended(): List<FeedPost> {
        feedPosts.addAll(apiService.loadNewsFeedResponse(getAccessToken()).mapResponseToPosts())
        return feedPosts
    }

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if(feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id,
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id,
            )
        }
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = feedPosts.indexOf(feedPost)
        feedPosts[postIndex] = newPost
    }

    override fun getFeedPosts(): List<FeedPost> = feedPosts.toList()

    private fun getAccessToken(): String {
        return token ?: throw IllegalArgumentException("Token shouldn't be equals null")
    }
}
