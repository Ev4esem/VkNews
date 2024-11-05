@file:OptIn(ExperimentalPagingApi::class)

package com.sumin.vknewsclient.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.sumin.vknewsclient.data.local.NewsFeedDatabase
import com.sumin.vknewsclient.data.local.entity.FeedPostEntity
import com.sumin.vknewsclient.data.mapper.mapResponseToPosts
import com.sumin.vknewsclient.data.mapper.toFeedPostListEntity
import com.sumin.vknewsclient.data.network.ApiService
import com.sumin.vknewsclient.data.network.model.NewsFeedResponseDto
import com.sumin.vknewsclient.domain.model.FeedPost
import com.vk.id.VKID
import okio.IOException
import java.util.concurrent.TimeUnit

class NewsFeedRemoteMediator(
    private val newsApi: ApiService,
    private val newsFeedDb: NewsFeedDatabase,
) : RemoteMediator<Int, FeedPostEntity>() {

    private var nextFrom: String? = null
    private val token = VKID.instance.accessToken?.token

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FeedPostEntity>
    ): MediatorResult {
        if (loadType == LoadType.PREPEND) return MediatorResult.Success(endOfPaginationReached = true)

        return try {
            val startFrom = nextFrom
            val newsFeedResponse = if (startFrom == null) {
                newsApi.loadNewsFeedResponse(token = getAccessToken(), 20)
            } else {
                newsApi.loadNewsFeedResponse(
                    token = getAccessToken(),
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
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private fun getAccessToken(): String {
        return token ?: throw IllegalArgumentException("Token shouldn't be equals null")
    }

}
