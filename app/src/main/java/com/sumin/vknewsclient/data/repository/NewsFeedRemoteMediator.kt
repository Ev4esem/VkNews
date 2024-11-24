@file:OptIn(ExperimentalPagingApi::class)

package com.sumin.vknewsclient.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.sumin.vknewsclient.VkNewsApp
import com.sumin.vknewsclient.data.local.NewsFeedDatabase
import com.sumin.vknewsclient.data.local.entity.FeedPostEntity
import com.sumin.vknewsclient.data.mapper.mapResponseToPosts
import com.sumin.vknewsclient.data.mapper.toFeedPostListEntity
import com.sumin.vknewsclient.data.network.ApiService
import okio.IOException
import java.lang.Exception

class NewsFeedRemoteMediator(
    private val newsApi : ApiService,
    private val newsFeedDb : NewsFeedDatabase,
) : RemoteMediator<Int, FeedPostEntity>() {

    private var nextFrom : String? = null

    override suspend fun initialize() : InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType : LoadType,
        state : PagingState<Int, FeedPostEntity>
    ) : MediatorResult {
        if (loadType == LoadType.PREPEND) return MediatorResult.Success(endOfPaginationReached = true)
        return try {
            val startFrom = nextFrom
            val newsFeedResponse = if (startFrom == null) {
                newsApi.loadNewsFeedResponse(token = VkNewsApp().getUserToken(), 20)
            } else {
                newsApi.loadNewsFeedResponse(
                    token = VkNewsApp().getUserToken(),
                    startFrom = startFrom,
                    count = 20
                )
            }
            nextFrom = newsFeedResponse.newsFeedContentDto.nextFrom
            val newsFeedItems = newsFeedResponse.mapResponseToPosts()
            val endOfPaginationReached = newsFeedItems.isEmpty()

            newsFeedDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsFeedDb.newsFeedDao.clearAll()
                }
                newsFeedDb.newsFeedDao.upsertAll(newsFeedItems.toFeedPostListEntity())
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e : IOException) {
            getDataDependOnCachedData(
                newsFeedDb = newsFeedDb,
                exception = e,
            )
        } catch (e : HttpException) {
            getDataDependOnCachedData(
                newsFeedDb = newsFeedDb,
                exception = e,
            )
        }
    }

    private suspend fun getDataDependOnCachedData(
        newsFeedDb : NewsFeedDatabase,
        exception : Exception
    ) : MediatorResult {
        val pagingSource = newsFeedDb.newsFeedDao.pagingSource()
        val cachedData = pagingSource.load(PagingSource.LoadParams.Refresh(0, 20, false))
        return if (cachedData is PagingSource.LoadResult.Page) {
            MediatorResult.Success(endOfPaginationReached = false)
        } else {
            MediatorResult.Error(exception)
        }
    }
}
