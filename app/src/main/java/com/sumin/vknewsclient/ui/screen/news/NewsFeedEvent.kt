package com.sumin.vknewsclient.ui.screen.news

import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.domain.model.StatisticItem

sealed interface NewsFeedEvent {

    data class UpdateCount(val feedPost: FeedPost, val statisticItem: StatisticItem): NewsFeedEvent

    data class ChangeLikeStatus(val feedPost: FeedPost): NewsFeedEvent

}
