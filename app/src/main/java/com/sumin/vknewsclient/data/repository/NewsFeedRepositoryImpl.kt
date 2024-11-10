package com.sumin.vknewsclient.data.repository

import com.sumin.vknewsclient.data.local.NewsFeedDatabase
import com.sumin.vknewsclient.data.mapper.toFeedPostEntity
import com.sumin.vknewsclient.data.network.ApiService
import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.domain.model.StatisticItem
import com.sumin.vknewsclient.domain.model.StatisticType
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import com.vk.id.VKID
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService : ApiService,
    private val newsFeedDatabase : NewsFeedDatabase,
) : NewsFeedRepository {

    private var userToken = VKID.instance.accessToken?.token
        ?: throw IllegalArgumentException("Token shouldn't be equals null")

    override suspend fun changeLikeStatus(feedPost : FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = userToken,
                ownerId = feedPost.communityId,
                postId = feedPost.id,
            )
        } else {
            apiService.addLike(
                token = userToken,
                ownerId = feedPost.communityId,
                postId = feedPost.id,
            )
        }
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            val indexItem = indexOfFirst { it.type == StatisticType.LIKES }
            this[indexItem] = StatisticItem(StatisticType.LIKES, newLikesCount)
        }
        val newPost = feedPost.copy(
            statistics = newStatistics,
            isLiked = !feedPost.isLiked
        )
        newsFeedDatabase.newsFeedDao.insertFeedPost(newPost.toFeedPostEntity())
    }
}
