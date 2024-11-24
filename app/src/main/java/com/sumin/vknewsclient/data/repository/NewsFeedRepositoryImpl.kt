package com.sumin.vknewsclient.data.repository

import com.sumin.vknewsclient.VkNewsApp
import com.sumin.vknewsclient.data.local.NewsFeedDatabase
import com.sumin.vknewsclient.data.mapper.toComments
import com.sumin.vknewsclient.data.mapper.toFeedPostEntity
import com.sumin.vknewsclient.data.network.ApiService
import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.domain.model.PostComment
import com.sumin.vknewsclient.domain.model.StatisticItem
import com.sumin.vknewsclient.domain.model.StatisticType
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService : ApiService,
    private val newsFeedDatabase : NewsFeedDatabase,
) : NewsFeedRepository {

    override suspend fun changeLikeStatus(feedPost : FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = VkNewsApp().getUserToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id,
            )
        } else {
            apiService.addLike(
                token = VkNewsApp().getUserToken(),
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

    override fun getCommentsById(feedPost : FeedPost) : Flow<List<PostComment>> = flow {
         val comments = apiService.getCommentsByPost(
             token = VkNewsApp().getUserToken(),
             ownerId = feedPost.communityId,
             postId = feedPost.id,
         ).toComments()
         emit(comments)
    }
}
