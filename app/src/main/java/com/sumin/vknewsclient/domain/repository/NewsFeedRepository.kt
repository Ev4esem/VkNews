package com.sumin.vknewsclient.domain.repository

import com.sumin.vknewsclient.domain.model.FeedPost

interface NewsFeedRepository {

    suspend fun changeLikeStatus(feedPost: FeedPost)

}
