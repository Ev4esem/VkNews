package com.sumin.vknewsclient.domain.repository

import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.domain.model.Friend
import com.sumin.vknewsclient.domain.model.PostComment
import com.sumin.vknewsclient.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface NewsFeedRepository {

    suspend fun changeLikeStatus(feedPost: FeedPost)

    fun getCommentsById(feedPost : FeedPost): Flow<List<PostComment>>

    fun getProfileInfo(): Flow<Profile>

    fun getFriends(): Flow<List<Friend>>

}
