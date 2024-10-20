package com.sumin.vknewsclient.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sumin.vknewsclient.data.local.entity.StatisticItemEntity


class StatisticItemEntityConverter {

    @TypeConverter
    fun fromStatisticItemEntity(value: List<StatisticItemEntity>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun dateToTimestamp(value: String): List<StatisticItemEntity> {
        val typeToken = object : TypeToken<List<StatisticItemEntity>>() {}.type
        return Gson().fromJson(value, typeToken)
    }

}
