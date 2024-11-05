package com.sumin.vknewsclient.di

import android.content.Context
import androidx.room.Room
import com.sumin.vknewsclient.data.local.NewsFeedDao
import com.sumin.vknewsclient.data.local.NewsFeedDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): NewsFeedDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NewsFeedDatabase::class.java,
            "newsfeed_database.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: NewsFeedDatabase): NewsFeedDao = database.newsFeedDao

}
