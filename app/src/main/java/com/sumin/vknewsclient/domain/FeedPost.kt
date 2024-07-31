package com.sumin.vknewsclient.domain

import com.sumin.vknewsclient.R

data class FeedPost(
    val id: Int = 0,
    val communityName: String = "dev/null",
    val timePost: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 300),
        StatisticItem(StatisticType.COMMENTS, 400),
        StatisticItem(StatisticType.LIKES, 500),
        StatisticItem(StatisticType.SHARES, 100),
    )
)
