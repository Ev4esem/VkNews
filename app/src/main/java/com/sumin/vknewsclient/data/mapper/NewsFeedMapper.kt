package com.sumin.vknewsclient.data.mapper

import com.sumin.vknewsclient.data.model.NewsFeedResponseDto
import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.domain.model.StatisticItem
import com.sumin.vknewsclient.domain.model.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

fun NewsFeedResponseDto.mapResponseToPosts(): List<FeedPost> {
    val result = mutableListOf<FeedPost>()
    val posts = this.newsFeedContentDto.posts
    val group = this.newsFeedContentDto.groups
    for (post in posts) {
        val group = group.find { it.id == post.communityId.absoluteValue } ?: break
        val feedPost = FeedPost(
            id = post.id,
            communityName = group.name,
            publicationDate = post.date.mapTimestampToDate(),
            avatarUrl = group.imageUrl,
            contentText = post.text,
            contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
            statistics = listOf(
                StatisticItem(type = StatisticType.LIKES, post.likes.count),
                StatisticItem(type = StatisticType.VIEWS, post.views.count),
                StatisticItem(type = StatisticType.COMMENTS, post.comments.count),
                StatisticItem(type = StatisticType.SHARES, post.reposts.count),
            ),
            isLiked = post.likes.userLikes > 0,
            communityId = post.communityId,
        )
        result.add(feedPost)
    }
    return result
}

private fun Long.mapTimestampToDate(): String {
    // We get time in second, not millisecond
    val date = Date(this * 1000)
    return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
}
