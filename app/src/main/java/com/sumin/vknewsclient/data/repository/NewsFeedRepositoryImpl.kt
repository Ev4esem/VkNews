package com.sumin.vknewsclient.data.repository

import com.sumin.vknewsclient.data.mapper.mapResponseToPosts
import com.sumin.vknewsclient.data.network.ApiService
import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.domain.model.StatisticItem
import com.sumin.vknewsclient.domain.model.StatisticType
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.refresh.VKIDRefreshTokenCallback
import com.vk.id.refresh.VKIDRefreshTokenFail
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
   private val apiService: ApiService
) : NewsFeedRepository {

    private var userToken = VKID.instance.accessToken?.token

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost> get() = _feedPosts.toList()

    private val nextFrom: String? = null

    override suspend fun loadRecommended(): List<FeedPost> {
//        val startFrom = nextFrom
//        if (startFrom == null && feedPosts.isNotEmpty()) return feedPosts
//
//        val response = if(startFrom == null) {
//            apiService.loadNewsFeedResponse(getAccessToken())
//        } else {
//            apiService.loadNewsFeedResponse(getAccessToken(), startFrom)
//        }
//        val posts = response.mapResponseToPosts()
//        _feedPosts.addAll(posts)
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
        _feedPosts[postIndex] = newPost
    }

    private fun getAccessToken(): String {
        return userToken ?: throw IllegalArgumentException("Token shouldn't be equals null")
    }
}
