package com.sumin.vknewsclient.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sumin.vknewsclient.data.local.converter.StatisticItemEntityConverter
import com.sumin.vknewsclient.data.local.entity.FeedPostEntity

@Database(
    entities = [FeedPostEntity::class],
    version = 1
)
@TypeConverters(StatisticItemEntityConverter::class)
abstract class NewsFeedDatabase: RoomDatabase() {

    abstract val newsFeedDao: NewsFeedDao

}
