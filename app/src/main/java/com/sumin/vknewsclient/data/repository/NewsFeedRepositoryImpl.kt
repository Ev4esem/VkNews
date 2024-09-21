package com.sumin.vknewsclient.data.repository

import com.sumin.vknewsclient.data.mapper.mapResponseToPosts
import com.sumin.vknewsclient.data.network.ApiFactory
import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.domain.model.StatisticItem
import com.sumin.vknewsclient.domain.model.StatisticType
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import com.vk.id.VKID

class NewsFeedRepositoryImpl : NewsFeedRepository {

    private val token = VKID.instance.accessToken?.token
    private val apiService = ApiFactory.apiService

     val feedPosts = mutableListOf<FeedPost>()

    override suspend fun loadRecommended(): List<FeedPost> {
        val response = apiService.loadNewsFeedResponse(getAccessToken())
        val posts = response.mapResponseToPosts()
        feedPosts.addAll(posts)
        return feedPosts
    }

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
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

    private fun getAccessToken(): String {
        return token ?: throw IllegalArgumentException("Token shouldn't be equals null")
    }
}
