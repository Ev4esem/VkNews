package com.sumin.vknewsclient.domain.repository

import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.domain.model.PostComment
import kotlinx.coroutines.flow.Flow

interface NewsFeedRepository {

    suspend fun changeLikeStatus(feedPost: FeedPost)

    fun getCommentsById(feedPost : FeedPost): Flow<List<PostComment>>

}
