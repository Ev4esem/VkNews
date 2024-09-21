package com.sumin.vknewsclient.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class StatisticItemEntity(
    val type: StatisticTypeEntity,
    val count: Int
) : Parcelable

enum class StatisticTypeEntity {
    VIEWS, COMMENTS, SHARES, LIKES
}
