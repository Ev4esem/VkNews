package com.sumin.vknewsclient.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FeedPostEntity(
    @PrimaryKey
    val id: Long,
    val communityId: Long,
    val communityName: String,
    val publicationDate: String,
    val avatarUrl: String,
    val contentText: String,
    val contentImageUrl: String?,
    val statistics: List<StatisticItemEntity>,
    val isLiked: Boolean,
)
