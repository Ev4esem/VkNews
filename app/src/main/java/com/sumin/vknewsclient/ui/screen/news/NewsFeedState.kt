package com.sumin.vknewsclient.ui.screen.news

import androidx.paging.PagingData
import com.sumin.vknewsclient.domain.model.FeedPost
import kotlinx.coroutines.flow.Flow

data class NewsFeedScreenState(
    val loading: Boolean = false,
    val posts: Flow<PagingData<FeedPost>>? = null
)
