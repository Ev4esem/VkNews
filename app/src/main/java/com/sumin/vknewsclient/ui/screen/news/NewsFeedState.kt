package com.sumin.vknewsclient.ui.screen.news

import androidx.paging.PagingData
import com.sumin.vknewsclient.domain.model.FeedPost

data class NewsFeedState(
    val loading: Boolean = false,
    val loadState: PagingData<FeedPost> = PagingData.empty(),
    val error: String? = null,
)
