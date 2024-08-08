package com.sumin.vknewsclient.domain

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.sumin.vknewsclient.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedPost(
    val id: Int,
    val communityName: String,
    val timePost: String,
    val avatarResId: Int,
    val contentText: String,
    val contentImageResId: Int,
    val statistics: List<StatisticItem>
): Parcelable
