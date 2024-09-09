package com.sumin.vknewsclient.ui.screen.news

import com.sumin.vknewsclient.domain.model.FeedPost

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    data class Posts(val posts: List<FeedPost>) : NewsFeedScreenState()
}
