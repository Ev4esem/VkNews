package com.sumin.vknewsclient.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sumin.vknewsclient.data.local.NewsFeedDatabase
import com.sumin.vknewsclient.data.local.entity.FeedPostEntity
import com.sumin.vknewsclient.data.network.ApiService
import com.sumin.vknewsclient.data.repository.NewsFeedRemoteMediator
import com.sumin.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DatabaseModule::class, NetworkModule::class])
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindNewsFeedRepositoryImpl_to_bindNewsRepository(
        newsFeedRepositoryImpl: NewsFeedRepositoryImpl
    ) : NewsFeedRepository

    companion object {
        @OptIn(ExperimentalPagingApi::class)
        @Provides
        fun provideNewsFeedPager(database: NewsFeedDatabase, apiService: ApiService): Pager<Int, FeedPostEntity> {
            return Pager(
                config = PagingConfig(pageSize = 20),
                remoteMediator = NewsFeedRemoteMediator(
                    newsFeedDb = database,
                    newsApi = apiService
                ),
                pagingSourceFactory = database.newsFeedDao::pagingSource,
            )
        }
    }

}
