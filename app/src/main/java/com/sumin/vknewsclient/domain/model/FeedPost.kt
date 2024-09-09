package com.sumin.vknewsclient.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedPost(
    val id: Long,
    val communityId: Long,
    val communityName: String,
    val publicationDate: String,
    val avatarUrl: String,
    val contentText: String,
    val contentImageUrl: String?,
    val statistics: List<StatisticItem>,
    val isLiked: Boolean,
): Parcelable {
    companion object {
        val DEFAULT = FeedPost(
            id = 1,
            communityName = "Group 1",
            publicationDate = "14.02.2006",
            avatarUrl = "image",
            contentText = "Something text Something text Something text Something text Something text",
            contentImageUrl = "",
            statistics = listOf(
                StatisticItem(
                    StatisticType.LIKES, 130,
                ),
                StatisticItem(
                    StatisticType.VIEWS, 130,
                ),
                StatisticItem(
                    StatisticType.COMMENTS, 130,
                ),
                StatisticItem(
                    StatisticType.SHARES, 130,
                ),
            ),
            isLiked = true,
            communityId = 203
        )
    }
}
